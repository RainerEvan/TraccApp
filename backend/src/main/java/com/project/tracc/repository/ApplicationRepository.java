package com.project.tracc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Applications;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, UUID>{

    Boolean existsByName(String name);

}
