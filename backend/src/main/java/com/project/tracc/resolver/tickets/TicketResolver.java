package com.project.tracc.resolver.tickets;

import java.util.List;

import graphql.kickstart.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Supports;
import com.project.tracc.model.TicketAttachments;
import com.project.tracc.model.Tickets;
import com.project.tracc.service.SupportService;
import com.project.tracc.service.TicketAttachmentService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TicketResolver implements GraphQLResolver<Tickets> {
    
    @Autowired
    private final SupportService supportService;
    @Autowired
    private final TicketAttachmentService ticketAttachmentService;

    public Supports getSupport(Tickets ticket){
        return supportService.getSupportForTicket(ticket);
    }

    public List<TicketAttachments> getAttachments(Tickets ticket){
        return ticketAttachmentService.getAllFilesForTicket(ticket);
    }
}
