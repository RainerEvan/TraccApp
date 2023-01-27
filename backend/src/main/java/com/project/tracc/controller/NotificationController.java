package com.project.tracc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tracc.model.Notifications;
import com.project.tracc.payload.request.NotificationRequest;
import com.project.tracc.service.NotificationService;
import com.project.tracc.utils.ResponseHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {
    
    @Autowired
    private final NotificationService notificationService;

    @PostMapping(path = "/test")
    public ResponseEntity<Object> test(@RequestBody UUID id){
        try {
            Notifications notification = notificationService.getNotification(id);

            String result = notificationService.sendEmail(notification);
        
            return ResponseHandler.generateResponse("Push Notifications", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addNotification(@RequestBody NotificationRequest notificationRequest){
        try {
            Notifications notification = notificationService.addNotification(notificationRequest);

            String push = notificationService.sendPushNotification(notification);
            log.info("Push notification: {}",push);

            String email = notificationService.sendEmail(notification);
            log.info("Send email: {}",email);
        
            return ResponseHandler.generateResponse("Notification has been added successfully!", HttpStatus.OK, notification.getCreatedAt());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/{notificationId}")
    public void readNotification(@PathVariable("notificationId") UUID notificationId){
        notificationService.readNotification(notificationId);
    }
}
