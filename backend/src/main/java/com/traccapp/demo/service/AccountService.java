package com.traccapp.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.model.Roles;
import com.traccapp.demo.payload.request.AccountRequest;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.DivisionRepository;
import com.traccapp.demo.repository.RoleRepository;
import com.traccapp.demo.utils.ProfileImageUtils;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final DivisionRepository divisionRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthService authService;

    @Transactional
    public List<Accounts> getAllAccounts(){
        return accountRepository.findAll();
    }

    // @Transactional
    // public List<Accounts> getAllAccountsByRole(ERoles name){
    //     Roles role = roleRepository.findByName(name)
    //         .orElseThrow(() -> new AbstractGraphQLException("Role with current name cannot be found"+name,"roleName"));

    //     return accountRepository.findAllByRoles(role);
    // }

    @Transactional
    public List<Accounts> getAllDevelopers(){
        Roles role = roleRepository.findByName(ERoles.DEVELOPER)
            .orElseThrow(() -> new AbstractGraphQLException("Role with current name cannot be found: "+ERoles.DEVELOPER,"roleName"));

        return accountRepository.findAllByRoles(role);
    }

    public Accounts getAccount(UUID accountId){
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));
    }

    @Transactional
    public Accounts addAccount(MultipartFile file, AccountRequest accountRequest) {

        String username = accountRequest.getUsername();
        
        if(accountRepository.existsByUsername(username)){
            throw new IllegalStateException("Account with current username already exists: "+username);
        }

        Accounts account = new Accounts();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setFullname(accountRequest.getFullname());
        account.setEmail(accountRequest.getEmail());
        account.setContactNo(accountRequest.getContactNo());
        account.setIsActive(accountRequest.getIsActive());

        Divisions division = divisionRepository.findById(accountRequest.getDivisionId())
            .orElseThrow(() -> new IllegalStateException("Division with current id cannot be found: "+accountRequest.getDivisionId()));

        account.setDivision(division);
        account.setProfileImg(addImage(file));

        Roles role = roleRepository.findByName(accountRequest.getRolesName())
            .orElseThrow(() -> new IllegalStateException("Role with current name cannot be found "+accountRequest.getRolesName()));

        Set<Roles> roleSet = new HashSet<>();
        roleSet.add(role);

        account.setRoles(roleSet);

        return accountRepository.save(account);
    }

    @Transactional
    public Accounts editAccount(MultipartFile file, UUID accountId, AccountRequest accountRequest) {
        
        Accounts account = getAccount(accountId);

        String username = accountRequest.getUsername();
        String fullname = accountRequest.getFullname();
        String email = accountRequest.getEmail();
        String contactNo = accountRequest.getContactNo();
        UUID divisionId = accountRequest.getDivisionId();
        ERoles rolesName = accountRequest.getRolesName();
        Boolean isActive = accountRequest.getIsActive();

        if(accountRepository.existsByUsername(username)){
            throw new IllegalStateException("Account with current username already exists: "+username);
        }

        if(username != null && username.length() > 0 && !Objects.equals(account.getUsername(), username)){
            account.setUsername(username);
        }

        if(fullname != null && fullname.length() > 0 && !Objects.equals(account.getFullname(), fullname)){
            account.setFullname(fullname);
        }

        if(email != null && email.length() > 0 && !Objects.equals(account.getEmail(), email)){
            account.setEmail(email);
        }

        if(contactNo != null && contactNo.length() > 0 && !Objects.equals(account.getContactNo(), contactNo)){
            account.setContactNo(contactNo);
        }

        if(file != null){
            account.setProfileImg(addImage(file));
        }

        if(divisionId != null && !Objects.equals(account.getDivision().getId(), divisionId)){
            Divisions division = divisionRepository.findById(divisionId)
                .orElseThrow(() -> new IllegalStateException("Division with current id cannot be found: "+divisionId));

            account.setDivision(division);
        }

        if(rolesName != null){
            Roles role = roleRepository.findByName(rolesName)
                .orElseThrow(() -> new IllegalStateException("Role with current name cannot be found "+rolesName));

            if(!account.getRoles().contains(role)){
                Set<Roles> roleSet = new HashSet<>();
                roleSet.add(role);

                account.setRoles(roleSet);
            }
        }

        if(isActive != null && !Objects.equals(account.getIsActive(), isActive)){
            account.setIsActive(isActive);
        }

        return accountRepository.save(account);
    }

    @Transactional
    public void changePassword(String currentPassword, String newPassword){
        
        Accounts account = authService.getCurrentAccount();

        if(!passwordEncoder.matches(currentPassword, account.getPassword())){
            throw new IllegalStateException("The current password is not correct");
        }

        if(newPassword != null && newPassword.length() > 0){
            account.setPassword(passwordEncoder.encode(newPassword));
        }

        accountRepository.save(account);
    }

    public String addImage(MultipartFile file){
        try{
            byte[] image = new byte[0];

            File defaultImg = new File("src/main/resources/image/profile.jpg");
            image = FileUtils.readFileToByteArray(defaultImg);
            
            if(file != null){
                image = ProfileImageUtils.cropImageSquare(file);
            }

            String encodedString = Base64.getEncoder().encodeToString(image);

            return encodedString;
            
        } catch (IOException exception){
            throw new IllegalStateException("Could not add the current file", exception);
        }
    }

    public byte[] getProfileImage(UUID accountId){

        Accounts account = getAccount(accountId);

        byte[] decodedBytes = Base64.getDecoder().decode(account.getProfileImg());

        return decodedBytes;
    }
}
