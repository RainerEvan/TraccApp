package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.model.TicketLogs;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.TicketLogsRepository;
import com.traccapp.demo.repository.TicketRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class TicketLogsService {
    
    @Autowired
    private final TicketLogsRepository ticketLogsRepository;
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final AuthService authService;

    public List<TicketLogs> getAllTicketLogsForTicket(UUID ticketId){
        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+ticketId));

        return ticketLogsRepository.findAllByTicket(ticket);
    }

    public TicketLogs addTicketLogs(UUID ticketId, String action){
        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+ticketId));

        TicketLogs ticketLogs = new TicketLogs();
        ticketLogs.setTicket(ticket);
        ticketLogs.setActor(authService.getCurrentAccount());
        ticketLogs.setAction(action);
        ticketLogs.setStatus(ticket.getStatus());
        ticketLogs.setDateTime(OffsetDateTime.now());

        return ticketLogs;
    }

    public void logTicketInfo(TicketLogs ticketLogs){
        log.info("Ticket Log: Ticket {}, {}, Status {}, By {}",ticketLogs.getTicket().getTicketNo(),ticketLogs.getAction(),ticketLogs.getStatus().getName(),ticketLogs.getActor().getUsername());
    }
}
