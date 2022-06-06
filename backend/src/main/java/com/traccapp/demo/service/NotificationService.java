package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Notifications;
import com.traccapp.demo.payload.request.NotificationRequest;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final AccountRepository accountRepository;

    public List<Notifications> getAllNotificationsForAccount(UUID accountId){
        Accounts account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return notificationRepository.findAllByReceiver(account);
    }

    public Notifications getNotification(UUID notificationId){
        return notificationRepository.findById(notificationId)
            .orElseThrow(() -> new IllegalStateException("Notification with current id cannot be found: "+notificationId));
    }

    public Notifications addNotification(NotificationRequest notificationRequest){
        Accounts account = accountRepository.findById(notificationRequest.getReceiverId())
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+notificationRequest.getReceiverId(),"accountId"));

        Notifications notification = new Notifications();
        notification.setReceiver(account);
        notification.setCreatedAt(OffsetDateTime.now());
        notification.setTitle(notificationRequest.getTitle());
        notification.setBody(notificationRequest.getBody());

        return notificationRepository.save(notification);
    }

    public void readNotification(UUID notificationId){
        Notifications notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new IllegalStateException("Notification with current id cannot be found: "+notificationId));

        if(notification.getReadAt() == null){
            notification.setReadAt(OffsetDateTime.now());
            notificationRepository.save(notification);
        }
    }
}
