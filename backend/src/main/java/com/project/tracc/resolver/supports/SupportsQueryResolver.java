package com.project.tracc.resolver.supports;

import java.util.List;
import java.util.UUID;

import graphql.kickstart.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Supports;
import com.project.tracc.service.SupportService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SupportsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final SupportService supportService;

    public List<Supports> getAllSupportsForDeveloper(UUID accountId){
        return supportService.getAllSupportsForDeveloper(accountId);
    }
}
