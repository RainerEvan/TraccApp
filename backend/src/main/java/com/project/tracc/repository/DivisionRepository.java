package com.project.tracc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Divisions;

@Repository
public interface DivisionRepository extends JpaRepository<Divisions, UUID> {
    Boolean existsByName(String name);
}
