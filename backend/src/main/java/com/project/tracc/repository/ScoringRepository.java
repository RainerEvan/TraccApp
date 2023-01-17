package com.project.tracc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Scorings;

@Repository
public interface ScoringRepository extends JpaRepository<Scorings,UUID>{
    
}
