package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.Notifications;
import com.traccapp.demo.payload.request.NotificationRequest;
import com.traccapp.demo.service.NotificationService;
import com.traccapp.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {
    
    @Autowired
    private final NotificationService notificationService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addNotification(@RequestBody NotificationRequest notificationRequest){
        try {
            Notifications notification = notificationService.addNotification(notificationRequest);
        
            return ResponseHandler.generateResponse("Notification has been sent successfully!", HttpStatus.OK, notification.getCreatedAt());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/{notificationId}")
    public void readNotification(@PathVariable("notificationId") UUID notificationId){
        notificationService.readNotification(notificationId);
    }

    @GetMapping(path = "/test")
    public String test(@RequestParam("fcmToken") String fcmToken, @RequestParam("title") String title, @RequestParam("body") String body ){
        return notificationService.sendPushNotification(fcmToken, title, body);
    }
}
