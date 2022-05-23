package com.traccapp.demo.resolver.supports;

import java.util.List;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.service.SupportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SupportsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final SupportService supportService;
    
    public Supports getSupportForTicket(UUID ticketId){
        return supportService.getSupportForTicket(ticketId);
    }

    public List<Supports> getAllSupportsForDeveloper(UUID accountId){
        return supportService.getAllSupportsForDeveloper(accountId);
    }
}
