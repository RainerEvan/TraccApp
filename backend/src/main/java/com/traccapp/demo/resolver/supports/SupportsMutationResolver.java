package com.traccapp.demo.resolver.supports;

import java.util.Set;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.service.SupportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SupportsMutationResolver implements GraphQLMutationResolver{
    
    @Autowired
    private final SupportService supportService;

    public Supports addSupport(UUID ticketId){
        return supportService.addSupport(ticketId);
    }

    public Supports solveSupport(UUID supportId, String result, String description, String devNote, Set<String> tags){
        return supportService.solveSupport(supportId, result, description, devNote, tags);
    }

    public Supports withdrawSupport(UUID supportId, String result, String description){
        return supportService.withdrawSupport(supportId, result, description);
    }
}
