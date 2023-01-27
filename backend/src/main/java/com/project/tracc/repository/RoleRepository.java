package com.project.tracc.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.data.ERoles;
import com.project.tracc.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles,UUID>{
    Optional<Roles> findByName(ERoles name);
}
