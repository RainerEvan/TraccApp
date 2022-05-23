package com.traccapp.demo.service;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.repository.AccountRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final AccountRepository accountRepository;

    public Accounts getCurrentAccount(){
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        
        return accountRepository.findByUsername(principal)
            .orElseThrow(() -> new UsernameNotFoundException("Account with current username cannot be found: "+principal));
    }

    
}
