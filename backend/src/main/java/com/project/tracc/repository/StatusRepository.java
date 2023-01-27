package com.project.tracc.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.data.EStatus;
import com.project.tracc.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status,UUID>{

    Optional<Status> findByName(EStatus name);
    
}
