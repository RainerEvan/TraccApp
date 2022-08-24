package com.traccapp.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.FcmSubscriptions;

@Repository
public interface FcmSubscriptionRepository extends JpaRepository<FcmSubscriptions,UUID>{
    FcmSubscriptions findByAccount(Accounts account);
}
