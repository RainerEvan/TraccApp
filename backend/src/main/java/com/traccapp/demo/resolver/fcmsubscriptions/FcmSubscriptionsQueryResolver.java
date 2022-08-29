package com.traccapp.demo.resolver.fcmsubscriptions;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.FcmSubscriptions;
import com.traccapp.demo.service.FcmSubscriptionService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FcmSubscriptionsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final FcmSubscriptionService fcmSubscriptionService;

    public List<FcmSubscriptions> getAllFcmSubscriptionsForAccount(UUID accountId){
        return fcmSubscriptionService.getAllFcmSubscriptionsForAccount(accountId);
    }
}
