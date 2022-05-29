package com.traccapp.demo.service;

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
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.DivisionRepository;
import com.traccapp.demo.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public Accounts addAccount(String username, String password, String email, String contactNo, UUID divisionId, Boolean isActive, Set<ERoles> roles) {
        
        if(accountRepository.existsByUsername(username)){
            throw new AbstractGraphQLException("Account with current username already exists: "+username,"username");
        }

        Accounts account = new Accounts();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setEmail(email);
        account.setContactNo(contactNo);
        account.setIsActive(isActive);

        Divisions division = divisionRepository.findById(divisionId)
            .orElseThrow(() -> new AbstractGraphQLException("Division with current id cannot be found: "+divisionId,"divisionId"));

        account.setDivision(division);

        Set<Roles> roleSet = roles.stream()
            .map(role -> roleRepository.findByName(role).orElseThrow(() -> new AbstractGraphQLException("Role with current name cannot be found"+role,"roleName")))
            .collect(Collectors.toSet());

        account.setRoles(roleSet);

        return accountRepository.save(account);
    }

    public Accounts editAccount(UUID accountId, String username, String email, String contactNo, UUID divisionId, Boolean isActive) {
        
        Accounts account = getAccount(accountId);

        if(accountRepository.existsByUsername(username)){
            throw new AbstractGraphQLException("Account with current username already exists: "+username,"username");
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

        if(divisionId != null && !Objects.equals(account.getDivision().getId(), divisionId)){
            Divisions division = divisionRepository.findById(divisionId)
                .orElseThrow(() -> new AbstractGraphQLException("Division with current id cannot be found: "+divisionId,"divisionId"));

            account.setDivision(division);
        }

        if(isActive != null && !Objects.equals(account.getIsActive(), isActive)){
            account.setIsActive(isActive);
        }

        return accountRepository.save(account);
    }

    public void changePassword(UUID accountId, String currentPassword, String newPassword){
        
        Accounts account = getAccount(accountId);

        if(!Objects.equals(account.getPassword(), currentPassword)){
            throw new IllegalStateException("The current password is not correct");
        }

        if(newPassword != null && newPassword.length() > 0){
            account.setPassword(passwordEncoder.encode(newPassword));
        }

        accountRepository.save(account);
    }
}
