package com.traccapp.demo.repository;

import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, UUID> {
    Optional<Accounts> findByUsername(String username);
}
