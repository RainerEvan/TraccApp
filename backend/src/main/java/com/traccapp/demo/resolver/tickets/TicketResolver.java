package com.traccapp.demo.resolver.tickets;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.SupportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TicketResolver implements GraphQLResolver<Tickets> {
    
    @Autowired
    private final SupportRepository supportRepository;

    public Supports getSupport(Tickets ticket){
        return supportRepository.findByTicketAndIsActive(ticket, true)
            .orElseThrow(() -> new AbstractGraphQLException("Support for current ticket cannot be found"+ticket.getTicketNo(),"ticket"));
    }
}
