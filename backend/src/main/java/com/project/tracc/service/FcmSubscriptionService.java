package com.project.tracc.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Accounts;
import com.project.tracc.model.FcmSubscriptions;
import com.project.tracc.repository.AccountRepository;
import com.project.tracc.repository.FcmSubscriptionRepository;

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
