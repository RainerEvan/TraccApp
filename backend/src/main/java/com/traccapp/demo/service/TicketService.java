package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.request.TicketRequest;
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

    @Transactional
    public List<Tickets> getAllTickets(){
        return ticketRepository.findAll();
    }

    @Transactional
    public Tickets getTicket(UUID ticketId){
        return ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));
    }

    @Transactional
    public List<Tickets> getAllTicketsForUser(UUID accountId){
        Accounts user = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return ticketRepository.findAllByReporter(user);
    }

    @Transactional
    public Tickets addTicket(TicketRequest ticketRequest){

        Applications application = applicationRepository.findById(ticketRequest.getApplicationId())
            .orElseThrow(() -> new IllegalStateException("Application with current id cannot be found: "+ticketRequest.getApplicationId()));
        
        Tickets ticket = new Tickets();
        ticket.setApplication(application);
        ticket.setTitle(ticketRequest.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setReporter(authService.getCurrentAccount());
        ticket.setDateAdded(OffsetDateTime.now());

        Status status = statusRepository.findByName(EStatus.PENDING)
            .orElseThrow(() -> new IllegalStateException("Status with current name cannot be found: "+EStatus.PENDING));

        ticket.setStatus(status);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Tickets updateTicketStatus(UUID ticketId, EStatus statusName){
        Tickets ticket = ticketRepository.findByTicketId(ticketId).orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+ticketId));

        Status status = statusRepository.findByName(statusName)
            .orElseThrow(() -> new IllegalStateException("Status with current name cannot be found: "+statusName));

        ticket.setStatus(status);

        if(statusName.equals(EStatus.RESOLVED) || statusName.equals(EStatus.DROPPED)){
            ticket.setDateClosed(OffsetDateTime.now());
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void deleteTicket(UUID ticketId){
        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+ticketId));

        if(!ticket.getStatus().getName().equals(EStatus.PENDING)){
            throw new IllegalStateException("Ticket cannot be canceled, because it has already been taken to be resolved: "+ticket.getStatus().getName());
        }

        ticketRepository.delete(getTicket(ticketId));
    }
}
