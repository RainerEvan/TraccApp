package com.traccapp.demo.service;

import java.util.List;

import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
    
    @Autowired
    private final TicketRepository ticketRepository;

    public List<Tickets> getAllTickets(){
        return ticketRepository.findAll();
    }



}
