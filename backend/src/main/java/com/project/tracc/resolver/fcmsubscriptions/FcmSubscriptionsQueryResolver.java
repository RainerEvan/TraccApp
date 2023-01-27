package com.project.tracc.resolver.fcmsubscriptions;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.FcmSubscriptions;
import com.project.tracc.service.FcmSubscriptionService;

import graphql.kickstart.tools.GraphQLQueryResolver;
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
