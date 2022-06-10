package com.traccapp.demo.resolver.tickets;

import java.util.List;
import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

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
