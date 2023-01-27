package com.project.tracc.resolver.notifications;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Notifications;
import com.project.tracc.service.NotificationService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NotificationsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final NotificationService notificationService;

    public List<Notifications> getAllNotificationsForAccount(UUID accountId){
        return notificationService.getAllNotificationsForAccount(accountId);
    }

    public List<Notifications> getTopNotificationsForAccount(UUID accountId){
        return notificationService.getTopNotificationsForAccount(accountId);
    }

    public Notifications getNotification(UUID notificationId){
        return notificationService.getNotification(notificationId);
    }
}
