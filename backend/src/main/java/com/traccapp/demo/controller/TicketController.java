package com.traccapp.demo.controller;

import com.traccapp.demo.service.SupportService;
import com.traccapp.demo.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
