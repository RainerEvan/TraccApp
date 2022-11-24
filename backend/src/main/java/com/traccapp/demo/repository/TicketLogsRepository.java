package com.traccapp.demo.repository;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.TicketLogs;
import com.traccapp.demo.model.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketLogsRepository extends JpaRepository<TicketLogs,UUID> {
    List<TicketLogs> findAllByTicket(Tickets ticket);
}
