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
import com.traccapp.demo.repository.SupportRepository;
import com.traccapp.demo.repository.TagsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupportService {
    
    @Autowired
    private final SupportRepository supportRepository;
    @Autowired
    private final TicketService ticketService;
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TagsRepository tagsRepository;
    @Autowired
    private final TagsService tagsService;
    @Autowired
    private final AuthService authService;

    public Supports getSupport(UUID supportId){
        return supportRepository.findById(supportId).orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+supportId, "supportId"));
    }
    
    public Supports getSupportForTicket(UUID ticketId){

        Tickets ticket = ticketService.getTicket(ticketId);

        return supportRepository.findByTicketAndIsActive(ticket, true)
            .orElseThrow(() -> new AbstractGraphQLException("Support for current ticket id cannot be found: "+ticketId, "ticketId"));
    }

    public List<Supports> getAllSupportsForDeveloper(UUID accountId){

        Accounts developer = accountService.getAccount(accountId);
        
        return supportRepository.findAllByDeveloper(developer);
    }

    public Supports addSupport(UUID ticketId) {

        Supports support = new Supports();

        Tickets ticket = ticketService.getTicket(ticketId);
                
        support.setTicket(ticket);
        support.setDateTaken(LocalDate.now());
        support.setDeveloper(authService.getCurrentAccount());
        support.setIsActive(true);

        return supportRepository.save(support);
    }

    public Supports addSupport(UUID ticketId, UUID developerId) {

        Supports support = new Supports();

        Tickets ticket = ticketService.getTicket(ticketId);
                
        support.setTicket(ticket);
        support.setDateTaken(LocalDate.now());
        support.setDeveloper(accountService.getAccount(developerId));
        support.setIsActive(true);

        return supportRepository.save(support);
    }

    public Supports solveSupport(UUID supportId, String result, String description, String devNote, Set<String> tags) {
        
        Supports support = getSupport(supportId);

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
        Supports support = getSupport(supportId);

        support.setResult(result);
        support.setDescription(description);

        return supportRepository.save(support);
    }

    public void reassignSupport(UUID ticketId, UUID currSupportId, UUID developerId){
        Supports currSupport = getSupport(currSupportId);
        currSupport.setIsActive(false);

        Supports newSupport = addSupport(ticketId, developerId);
        supportRepository.save(newSupport);
    }

}