package com.traccapp.demo.resolver.accounts;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.service.AccountService;

import graphql.kickstart.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final AccountService accountService;

    public List<Accounts> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    public List<Accounts> getAllDevelopers(){
        return accountService.getAllDevelopers();
    }

    public Accounts getAccount(UUID accountId){
        return accountService.getAccount(accountId);
    }
}
