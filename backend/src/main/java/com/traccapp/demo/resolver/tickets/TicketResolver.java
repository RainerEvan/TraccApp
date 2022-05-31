package com.traccapp.demo.resolver.tickets;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.TicketAttachments;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.service.SupportService;
import com.traccapp.demo.service.TicketAttachmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TicketResolver implements GraphQLResolver<Tickets> {
    
    @Autowired
    private final SupportService supportService;
    @Autowired
    private final TicketAttachmentService ticketAttachmentService;

    public List<Supports> getSupport(Tickets ticket){
        return supportService.getSupportForTicket(ticket);
    }

    public List<TicketAttachments> getAttachments(Tickets ticket){
        return ticketAttachmentService.getAllFilesForTicket(ticket);
    }
}
