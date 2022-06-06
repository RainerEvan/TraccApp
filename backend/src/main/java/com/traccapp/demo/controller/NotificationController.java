package com.traccapp.demo.controller;

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

import com.traccapp.demo.model.Notifications;
import com.traccapp.demo.payload.request.NotificationRequest;
import com.traccapp.demo.service.NotificationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {
    
    @Autowired
    private final NotificationService notificationService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNotification(@RequestBody NotificationRequest notificationRequest){
        Notifications notification = notificationService.addNotification(notificationRequest);

        return ResponseEntity.status(HttpStatus.OK).body("New notification has been sent: "+notification.getCreatedAt());
    }

    @PutMapping(path = "/{notificationId}")
    public void readNotification(@PathVariable("notificationId") UUID notificationId){
        notificationService.readNotification(notificationId);
    }
}
