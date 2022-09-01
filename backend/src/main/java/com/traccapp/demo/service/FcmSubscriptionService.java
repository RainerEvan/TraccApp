package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

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
    @Autowired
    private final AuthService authService;

    @Transactional
    public List<FcmSubscriptions> getAllFcmSubscriptionsForAccount(UUID accountId){
        Accounts account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return fcmSubscriptionRepository.findAllByAccount(account);
    }

    @Transactional
    public FcmSubscriptions addFcmSubscriptions(String token){
        
        FcmSubscriptions fcmSubscription = new FcmSubscriptions();
        fcmSubscription.setAccount(authService.getCurrentAccount());
        fcmSubscription.setToken(token);
        fcmSubscription.setTimestamp(OffsetDateTime.now());

        return fcmSubscriptionRepository.save(fcmSubscription);
    }
}
