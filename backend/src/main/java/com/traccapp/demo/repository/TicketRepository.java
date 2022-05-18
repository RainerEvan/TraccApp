package com.traccapp.demo.repository;

import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.TicketPK;
import com.traccapp.demo.model.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, TicketPK> {
    Optional<Tickets> findById(UUID id);
    Optional<Tickets> findByTicketNo(String ticketNo);
}