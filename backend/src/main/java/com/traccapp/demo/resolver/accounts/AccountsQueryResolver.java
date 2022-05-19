package com.traccapp.demo.resolver.accounts;

import java.util.List;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final AccountRepository accountRepository;

    public List<Accounts> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Accounts getAccounts(UUID accountId){
        return accountRepository.findById(accountId).orElse(null);
    }
}
