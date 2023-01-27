package com.project.tracc.resolver.tickets;

import java.util.List;
import java.util.UUID;

import graphql.kickstart.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Tickets;
import com.project.tracc.service.TicketService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TicketsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final TicketService ticketService;

    public List<Tickets> getAllTickets(){
        return ticketService.getAllTickets();
    }

    public Tickets getTicket(UUID ticketId){
        return ticketService.getTicket(ticketId);
    }

    public List<Tickets> getAllTicketsForUser(UUID accountId){
        return ticketService.getAllTicketsForUser(accountId);
    }
}
