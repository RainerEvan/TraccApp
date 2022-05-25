package com.traccapp.demo.resolver.applications;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApplicationQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final ApplicationService applicationService;

    public List<Applications> getAllApplications(){
        return applicationService.getAllApplications();
    }
}
