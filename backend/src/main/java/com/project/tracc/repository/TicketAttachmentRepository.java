package com.project.tracc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.TicketAttachments;
import com.project.tracc.model.Tickets;

@Repository
public interface TicketAttachmentRepository extends JpaRepository<TicketAttachments,UUID>{
    List<TicketAttachments> findAllByTicket(Tickets ticket);
}
