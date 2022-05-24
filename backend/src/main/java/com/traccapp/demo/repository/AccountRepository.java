package com.traccapp.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, UUID> {
    List<Accounts> findAllByRoles(Roles role);
    Optional<Accounts> findByUsername(String username);
    Boolean existsByUsername(String username);
}
