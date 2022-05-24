package com.traccapp.demo.service;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.repository.StatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatusService {
    
    @Autowired
    private final StatusRepository statusRepository;

    public Status getStatus(EStatus name){
        return statusRepository.findByName(name)
            .orElseThrow(() -> new AbstractGraphQLException("Status with current name cannot be found: "+name, "statusName"));
    }
}
