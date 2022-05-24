package com.traccapp.demo.resolver.accounts;

import java.util.List;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.service.AccountService;

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

    public List<Accounts> getAllAccountsByRole(ERoles name){
        return accountService.getAllAccountsByRole(name);
    }

    public Accounts getAccounts(UUID accountId){
        return accountService.getAccount(accountId);
    }
}
