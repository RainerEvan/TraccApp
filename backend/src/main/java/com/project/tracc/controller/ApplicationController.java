package com.project.tracc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tracc.model.Applications;
import com.project.tracc.service.ApplicationService;
import com.project.tracc.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController {
    
    @Autowired
    private final ApplicationService applicationService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addApplication(@RequestBody String name){
        try {
            Applications application = applicationService.addApplication(name);
            
            return ResponseHandler.generateResponse("Application has been added successfully!", HttpStatus.OK, application.getName());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteApplication(@RequestParam("applicationId") UUID applicationId){
        try {
            applicationService.deleteApplication(applicationId);
            
            return ResponseHandler.generateResponse("Application has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
