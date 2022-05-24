package com.traccapp.demo.service;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.repository.ApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationService {
    
    @Autowired
    private final ApplicationRepository applicationRepository;

    public List<Applications> getAllApplications(){
        return applicationRepository.findAll();
    }

    public Applications getApplication(UUID applicationId){
        return applicationRepository.findById(applicationId)
            .orElseThrow(() -> new AbstractGraphQLException("Application with current id cannot be found: "+applicationId,"applicationId"));
    }

    public Applications addApplication(String name){

        if(applicationRepository.existsByName(name)){
            throw new AbstractGraphQLException("Application with current name already exists: "+name,"applicationName");
        }

        Applications application = new Applications();
        application.setName(name);
        return applicationRepository.save(application);
    }

    public void deleteApplication(UUID applicationId){
        applicationRepository.delete(getApplication(applicationId));
    }
}
