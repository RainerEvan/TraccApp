package com.traccapp.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traccapp.demo.model.ScoreConfigs;

@Repository
public interface ScoreConfigRepository extends JpaRepository<ScoreConfigs,UUID>{
    
}
