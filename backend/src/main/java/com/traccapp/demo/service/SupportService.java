package com.traccapp.demo.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tags;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.SupportRepository;
import com.traccapp.demo.repository.TagsRepository;
import com.traccapp.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupportService {
    
    @Autowired
    private final SupportRepository supportRepository;
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TagsRepository tagsRepository;
    @Autowired
    private final TagsService tagsService;
    @Autowired
    private final AuthService authService;

    public Supports getSupport(UUID supportId){
        return supportRepository.findById(supportId).orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+supportId, "supportId"));
    }
    
    public List<Supports> getSupportForTicket(Tickets ticket){
        return supportRepository.findAllByTicketAndIsActive(ticket, true);
    }

    public List<Supports> getAllSupportsForDeveloper(UUID accountId){

        Accounts developer = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));
        
        return supportRepository.findAllByDeveloper(developer);
    }

    public Supports addSupport(UUID ticketId) {

        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId")); 

        Supports support = new Supports();        
        support.setTicket(ticket);
        support.setDateTaken(LocalDate.now());
        support.setDeveloper(authService.getCurrentAccount());
        support.setIsActive(true);

        return supportRepository.save(support);
    }

    public Supports solveSupport(UUID supportId, String result, String description, String devNote, Set<String> tags) {
        
        Supports support = supportRepository
            .findById(supportId).orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+supportId, "supportId"));

        support.setResult(result);
        support.setDescription(description);
        support.setDevNote(devNote);

        Set<Tags> tagSet = new HashSet<>();
        
        for(String tag: tags){
            Tags currTag = new Tags();

            if(tagsRepository.existsByName(tag)){
                currTag = tagsService.getTags(tag);
            }

            currTag = tagsService.addTags(tag);
            
            tagSet.add(currTag);
        }

        support.setTags(tagSet);

        return supportRepository.save(support);
    }

    public Supports withdrawSupport(UUID supportId, String result, String description) {
        Supports support = supportRepository
            .findById(supportId).orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+supportId, "supportId"));

        support.setResult(result);
        support.setDescription(description);

        return supportRepository.save(support);
    }

    public Supports reassignSupport(UUID ticketId, UUID currSupportId, UUID developerId){
        Supports currSupport = supportRepository.findById(currSupportId)
            .orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+currSupportId, "supportId"));
        currSupport.setIsActive(false);
        supportRepository.save(currSupport);
        
        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

        Accounts developer = accountRepository.findById(developerId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+developerId,"accountId"));

        Supports newSupport = new Supports();          
        newSupport.setTicket(ticket);
        newSupport.setDateTaken(LocalDate.now());
        newSupport.setDeveloper(developer);
        newSupport.setIsActive(true);

        return supportRepository.save(newSupport);
    }

}
