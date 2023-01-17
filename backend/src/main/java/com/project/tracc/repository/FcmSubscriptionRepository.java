package com.project.tracc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Accounts;
import com.project.tracc.model.FcmSubscriptions;

@Repository
public interface FcmSubscriptionRepository extends JpaRepository<FcmSubscriptions,UUID>{
    List<FcmSubscriptions> findAllByAccount(Accounts account);
}
