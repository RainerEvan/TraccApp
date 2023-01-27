package com.project.tracc.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Applications;
import com.project.tracc.repository.ApplicationRepository;

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
            throw new IllegalStateException("Application with current name already exists: "+name);
        }

        Applications application = new Applications();
        application.setName(name);
        return applicationRepository.save(application);
    }

    public void deleteApplication(UUID applicationId){
        Applications application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalStateException("Application with current id cannot be found: "+applicationId));

        applicationRepository.delete(application);
    }
}
