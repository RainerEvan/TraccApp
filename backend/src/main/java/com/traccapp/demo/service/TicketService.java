package com.traccapp.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.ApplicationRepository;
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
    private final ApplicationRepository applicationRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final AuthService authService;

    public List<Tickets> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Tickets getTicket(UUID ticketId){
        return ticketRepository.findById(ticketId).orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));
    }

    public List<Tickets> getAllTicketsForUser(UUID accountId){
        Accounts user = accountRepository.findById(accountId).orElseThrow(() -> new AbstractGraphQLException("User with current id cannot be found: "+accountId, "accountId"));

        return ticketRepository.findAllByReporter(user);
    }

    public Tickets addTicket(UUID applicationId, String title, String description){
        Tickets ticket = new Tickets();
        
        Applications application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new AbstractGraphQLException("Application with current id cannot be found: "+applicationId,"applicationId"));
        
        ticket.setApplication(application);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setReporter(authService.getCurrentAccount());
        ticket.setDateAdded(LocalDate.now());

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(UUID ticketId){
        Tickets ticket = getTicket(ticketId);

        ticketRepository.delete(ticket);
    }

    public void closeTicket(UUID ticketId){

    }

    public void reassignTicket(UUID ticketId){

    }

    public void dropTicket(UUID ticketId){
        
    }
}
