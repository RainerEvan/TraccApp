package com.traccapp.demo.service;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Notifications;
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

    
}
