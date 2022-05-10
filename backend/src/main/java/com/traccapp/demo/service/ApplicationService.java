package com.traccapp.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.Applications;
import com.traccapp.demo.repository.ApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    
    @Autowired
    public ApplicationRepository applicationRepository;

    public List<Applications> getAllApplications(){
        return applicationRepository.findAll();
    }

    public Optional<Applications> findById(UUID id){
        return applicationRepository.findById(id);
    }

    public void save(Applications application){
        applicationRepository.save(application);
    }

    public void delete(UUID id){
        applicationRepository.deleteById(id);
    }
}
