package com.project.tracc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.TicketLogs;
import com.project.tracc.model.Tickets;

@Repository
public interface TicketLogsRepository extends JpaRepository<TicketLogs,UUID> {
    List<TicketLogs> findAllByTicket(Tickets ticket);
}
