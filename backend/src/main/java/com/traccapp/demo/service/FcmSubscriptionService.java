package com.traccapp.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.FcmSubscriptions;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.FcmSubscriptionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FcmSubscriptionService {
    
    @Autowired
    private final FcmSubscriptionRepository fcmSubscriptionRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public FcmSubscriptions getFcmSubscriptions(UUID accountId){
        Accounts account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return fcmSubscriptionRepository.findByAccount(account);
    }
}
