package com.project.tracc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Accounts;
import com.project.tracc.model.Notifications;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,UUID>{
    
    List<Notifications> findAllByReceiver(Accounts receiver);
    List<Notifications> findFirst3ByReceiverOrderByCreatedAtDesc(Accounts receiver);
}
