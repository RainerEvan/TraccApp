package com.traccapp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.FcmSubscriptions;
import com.traccapp.demo.service.FcmSubscriptionService;
import com.traccapp.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/fcmsubscription")
@AllArgsConstructor
public class FcmSubscriptionController {
    
    @Autowired
    private final FcmSubscriptionService fcmSubscriptionService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addFcmSubscription(@RequestParam("token") String token){
        try {
            FcmSubscriptions fcmSubscription = fcmSubscriptionService.addFcmSubscriptions(token);
        
            return ResponseHandler.generateResponse("Fcm Subscription has been saved successfully!", HttpStatus.OK, fcmSubscription.getTimestamp());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
