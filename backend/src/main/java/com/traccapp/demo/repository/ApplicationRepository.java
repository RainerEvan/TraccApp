package com.traccapp.demo.repository;

import java.util.UUID;

import com.traccapp.demo.model.Applications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, UUID>{

    Boolean existsByName(String name);

}
