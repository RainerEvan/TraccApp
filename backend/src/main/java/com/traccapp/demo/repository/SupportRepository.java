package com.traccapp.demo.repository;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<Supports, UUID>{
    List<Supports> findAllByTicketAndIsActive(Tickets ticket, Boolean isActive);
    List<Supports> findAllByDeveloper(Accounts developer);
}
