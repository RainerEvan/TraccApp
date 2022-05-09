package com.traccapp.demo.repository;

import java.util.UUID;

import com.traccapp.demo.model.Divisions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Divisions, UUID> {
    
}
