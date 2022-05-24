package com.traccapp.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {
    
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final ApplicationService applicationService;
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final StatusService statusService;
    @Autowired
    private final SupportService supportService;
    @Autowired
    private final AuthService authService;

    public List<Tickets> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Tickets getTicket(UUID ticketId){
        return ticketRepository.findById(ticketId).orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));
    }

    public List<Tickets> getAllTicketsForUser(UUID accountId){
        Accounts user = accountService.getAccount(accountId);

        return ticketRepository.findAllByReporter(user);
    }

    public Tickets addTicket(UUID applicationId, String title, String description){
        
        Tickets ticket = new Tickets();
        ticket.setApplication(applicationService.getApplication(applicationId));
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setReporter(authService.getCurrentAccount());
        ticket.setDateAdded(LocalDate.now());
        ticket.setStatus(statusService.getStatus(EStatus.PENDING));

        return ticketRepository.save(ticket);
    }

    public void takeTicket(UUID ticketId){
        Tickets ticket = getTicket(ticketId);
        ticket.setStatus(statusService.getStatus(EStatus.IN_PROGRESS));

        ticketRepository.save(ticket);
    }

    public void resolveTicket(UUID ticketId){
        Tickets ticket = getTicket(ticketId);
        ticket.setStatus(statusService.getStatus(EStatus.RESOLVED));

        ticketRepository.save(ticket);
    }

    public void reassignTicket(UUID ticketId, UUID currSupportId, UUID developerId){
        Tickets ticket = getTicket(ticketId);
        ticket.setStatus(statusService.getStatus(EStatus.IN_PROGRESS));

        supportService.reassignSupport(ticketId, currSupportId, developerId);

        ticketRepository.save(ticket);
    }

    public void dropTicket(UUID ticketId){
        Tickets ticket = getTicket(ticketId);
        ticket.setStatus(statusService.getStatus(EStatus.DROPPED));
        ticket.setDateClosed(LocalDate.now());

        ticketRepository.save(ticket);
    }

    public void closeTicket(UUID ticketId){
        Tickets ticket = getTicket(ticketId);
        ticket.setStatus(statusService.getStatus(EStatus.CLOSED));
        ticket.setDateClosed(LocalDate.now());

        ticketRepository.save(ticket);
    }

    public void deleteTicket(UUID ticketId){
        ticketRepository.delete(getTicket(ticketId));
    }
}
