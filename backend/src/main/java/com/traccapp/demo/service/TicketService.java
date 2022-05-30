package com.traccapp.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.ApplicationRepository;
import com.traccapp.demo.repository.StatusRepository;
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
    private final StatusRepository statusRepository;
    @Autowired
    private final AuthService authService;

    public List<Tickets> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Tickets getTicket(UUID ticketId){
        return ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));
    }

    public List<Tickets> getAllTicketsForUser(UUID accountId){
        Accounts user = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return ticketRepository.findAllByReporter(user);
    }

    public Tickets addTicket(UUID applicationId, String title, String description){

        Applications application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new AbstractGraphQLException("Application with current id cannot be found: "+applicationId,"applicationId"));
        
        Tickets ticket = new Tickets();
        ticket.setApplication(application);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setReporter(authService.getCurrentAccount());
        ticket.setDateAdded(LocalDate.now());

        Status status = statusRepository.findByName(EStatus.PENDING)
            .orElseThrow(() -> new AbstractGraphQLException("Status with current name cannot be found: "+EStatus.PENDING, "statusName"));

        ticket.setStatus(status);

        return ticketRepository.save(ticket);
    }

    public Tickets updateTicketStatus(UUID ticketId, EStatus statusName){
        Tickets ticket = ticketRepository.findByTicketId(ticketId).orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

        Status status = statusRepository.findByName(statusName)
            .orElseThrow(() -> new AbstractGraphQLException("Status with current name cannot be found: "+statusName, "statusName"));

        ticket.setStatus(status);

        if(statusName.equals(EStatus.CLOSED) || statusName.equals(EStatus.DROPPED)){
            ticket.setDateClosed(LocalDate.now());
        }

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(UUID ticketId){
        Tickets ticket = ticketRepository.findByTicketId(ticketId).orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

        if(!ticket.getStatus().getName().equals(EStatus.PENDING)){
            throw new IllegalStateException("Ticket cannot be canceled, because it has been taken to be resolved: "+ticket.getTicketNo());
        }

        ticketRepository.delete(getTicket(ticketId));
    }
}
