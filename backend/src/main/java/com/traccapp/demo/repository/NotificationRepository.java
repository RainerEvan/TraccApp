package com.traccapp.demo.repository;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Notifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,UUID>{
    
    List<Notifications> findAllByReceiver(Accounts receiver);
}
