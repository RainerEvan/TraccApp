package com.project.tracc.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Accounts;
import com.project.tracc.model.Roles;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, UUID> {
    List<Accounts> findAllByRole(Roles role);
    Optional<Accounts> findByUsername(String username);
    Boolean existsByUsername(String username);
}
