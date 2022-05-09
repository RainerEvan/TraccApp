package com.traccapp.demo.repository;

import java.util.UUID;

import com.traccapp.demo.model.TicketAttachments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketAttachmentsRepository extends JpaRepository<TicketAttachments,UUID>{
    
}
