package com.traccapp.demo.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.model.Roles;
import com.traccapp.demo.payload.request.AccountRequest;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.DivisionRepository;
import com.traccapp.demo.repository.RoleRepository;

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

    public List<Accounts> getAllAccounts(){
        return accountRepository.findAll();
    }

    public List<Accounts> getAllAccountsByRole(ERoles name){
        Roles role = roleRepository.findByName(name)
            .orElseThrow(() -> new AbstractGraphQLException("Role with current name cannot be found"+name,"roleName"));

        return accountRepository.findAllByRoles(role);
    }

    public Accounts getAccount(UUID accountId){
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));
    }

    public Accounts addAccount(MultipartFile file, AccountRequest accountRequest) {

        String username = accountRequest.getUsername();
        
        if(accountRepository.existsByUsername(username)){
            throw new IllegalStateException("Account with current username already exists: "+username);
        }

        Accounts account = new Accounts();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setEmail(accountRequest.getEmail());
        account.setContactNo(accountRequest.getContactNo());
        account.setIsActive(accountRequest.getIsActive());

        Divisions division = divisionRepository.findById(accountRequest.getDivisionId())
            .orElseThrow(() -> new IllegalStateException("Division with current id cannot be found: "+accountRequest.getDivisionId()));

        account.setDivision(division);

        if(file != null){
            account.setProfileImg(addImage(file));
        }

        Set<Roles> roleSet = accountRequest.getRoles().stream()
            .map(role -> roleRepository.findByName(role).orElseThrow(() -> new IllegalStateException("Role with current name cannot be found"+role)))
            .collect(Collectors.toSet());

        account.setRoles(roleSet);

        return accountRepository.save(account);
    }

    public Accounts editAccount(MultipartFile file, AccountRequest accountRequest) {
        
        Accounts account = getAccount(accountRequest.getId());

        String username = accountRequest.getUsername();
        String email = accountRequest.getEmail();
        String contactNo = accountRequest.getContactNo();
        UUID divisionId = accountRequest.getDivisionId();
        Boolean isActive = accountRequest.getIsActive();

        if(accountRepository.existsByUsername(username)){
            throw new IllegalStateException("Account with current username already exists: "+username);
        }

        if(username != null && username.length() > 0 && !Objects.equals(account.getUsername(), username)){
            account.setUsername(username);
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

        if(isActive != null && !Objects.equals(account.getIsActive(), isActive)){
            account.setIsActive(isActive);
        }

        return accountRepository.save(account);
    }

    public void changePassword(String currentPassword, String newPassword){
        
        Accounts account = authService.getCurrentAccount();

        if(!Objects.equals(account.getPassword(), currentPassword)){
            throw new IllegalStateException("The current password is not correct");
        }

        if(newPassword != null && newPassword.length() > 0){
            account.setPassword(passwordEncoder.encode(newPassword));
        }

        accountRepository.save(account);
    }

    public String addImage(MultipartFile file){
        try{
            String encodedString = Base64.getEncoder().encodeToString(file.getBytes());

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
