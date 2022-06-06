package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.Applications;
import com.traccapp.demo.service.ApplicationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController {
    
    @Autowired
    private final ApplicationService applicationService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addApplication(@RequestParam("application") String name){
        Applications application = applicationService.addApplication(name);
        
        return ResponseEntity.status(HttpStatus.OK).body("Application has been added successfully: "+application.getName());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteApplication(@RequestParam("applicationId") UUID applicationId){
        applicationService.deleteApplication(applicationId);

        return ResponseEntity.status(HttpStatus.OK).body("Application has been deleted!");
    }
}
