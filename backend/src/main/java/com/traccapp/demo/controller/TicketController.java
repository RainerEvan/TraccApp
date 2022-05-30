package com.traccapp.demo.controller;

import java.util.UUID;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.service.SupportService;
import com.traccapp.demo.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {
    
    @Autowired
    private final TicketService ticketService;
    @Autowired
    private final SupportService supportService;

    @PutMapping(path ="/update")
    public ResponseEntity<String> updateTicketStatus(@RequestParam("ticketId") UUID ticketId, @RequestParam("status") EStatus status){
        Tickets ticket = ticketService.updateTicketStatus(ticketId, status);

        return ResponseEntity.status(HttpStatus.OK).body("Ticket status has been updated: "+ticket.getTicketNo());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> cancelTicket(@RequestParam("ticketId") UUID ticketId){
        ticketService.deleteTicket(ticketId);

        return ResponseEntity.status(HttpStatus.OK).body("Ticket has been canceled!");
    }

    
}
