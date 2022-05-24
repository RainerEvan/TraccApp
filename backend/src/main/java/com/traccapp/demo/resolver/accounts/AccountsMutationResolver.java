package com.traccapp.demo.resolver.accounts;

import java.util.Set;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountsMutationResolver implements GraphQLMutationResolver{
    
    @Autowired
    private final AccountService accountService;

    public Accounts addAccount(String username, String password, String email, String contactNo, UUID divisionId, Boolean isActive, Set<ERoles> roles){
        return accountService.addAccount(username,password,email,contactNo,divisionId,isActive,roles);
    }

    public Accounts editAccount(UUID accountId, String username, String email, String contactNo, UUID divisionId, Boolean isActive){
        return accountService.editAccountAccount(accountId,username,email,contactNo,divisionId,isActive);
    }
}
