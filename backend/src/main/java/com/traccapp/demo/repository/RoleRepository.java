package com.traccapp.demo.repository;

import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.model.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,UUID>{
    Optional<Roles> findByName(ERoles name);
}
