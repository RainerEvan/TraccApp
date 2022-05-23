package com.traccapp.demo.resolver.tickets;

import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.traccapp.demo.model.Tickets;

import com.traccapp.demo.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TicketsMutationResolver implements GraphQLMutationResolver{

    @Autowired
    private final TicketService ticketService;

    public Tickets addTicket(UUID applicationId, String title, String description){
        return ticketService.addTicket(applicationId, title, description);
    }

}
