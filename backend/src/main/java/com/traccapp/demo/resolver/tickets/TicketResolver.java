package com.traccapp.demo.resolver.tickets;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.service.SupportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TicketResolver implements GraphQLResolver<Tickets> {
    
    @Autowired
    private final SupportService supportService;

    public List<Supports> getSupport(Tickets ticket){
        return supportService.getSupportForTicket(ticket);
    }
}
