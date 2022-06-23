package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tags;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.request.SupportRequest;
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
        return supportRepository.findById(supportId)
            .orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+supportId, "supportId"));
    }
    
    public List<Supports> getSupportForTicket(Tickets ticket){
        return supportRepository.findAllByTicketAndIsActive(ticket, true);
    }

    @Transactional
    public List<Supports> getAllSupportsForDeveloper(UUID accountId){

        Accounts developer = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));
        
        return supportRepository.findAllByDeveloper(developer);
    }

    public Supports addSupport(UUID ticketId) {

        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+ticketId)); 

        Supports support = new Supports();        
        support.setTicket(ticket);
        support.setDateTaken(OffsetDateTime.now());
        support.setDeveloper(authService.getCurrentAccount());
        support.setIsActive(true);

        return supportRepository.save(support);
    }

    public Supports solveSupport(SupportRequest supportRequest) {
        
        Supports support = supportRepository.findById(supportRequest.getSupportId())
            .orElseThrow(() -> new IllegalStateException("Support with current id cannot be found: "+supportRequest.getSupportId()));

        support.setResult(supportRequest.getResult());
        support.setDescription(supportRequest.getDescription());
        support.setDevNote(supportRequest.getDevNote());

        Set<String> tags = supportRequest.getTags();
        
        Set<Tags> tagSet = new HashSet<>();
        
        for(String tag: tags){
            Tags currTag = new Tags();

            if(tagsRepository.existsByName(tag)){
                currTag = tagsService.getTag(tag);
            }

            currTag = tagsService.addTag(tag);
            
            tagSet.add(currTag);
        }

        support.setTags(tagSet);

        return supportRepository.save(support);
    }

    public Supports withdrawSupport(UUID supportId, String result, String description) {
        Supports support = supportRepository
            .findById(supportId).orElseThrow(() -> new IllegalStateException("Support with current id cannot be found: "+supportId));

        support.setResult(result);
        support.setDescription(description);

        return supportRepository.save(support);
    }

    public Supports reassignSupport(UUID ticketId, UUID currSupportId, UUID developerId){
        Supports currSupport = supportRepository.findById(currSupportId)
            .orElseThrow(() -> new IllegalStateException("Support with current id cannot be found: "+currSupportId));
        currSupport.setIsActive(false);
        supportRepository.save(currSupport);
        
        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+ticketId));

        Accounts developer = accountRepository.findById(developerId)
            .orElseThrow(() -> new IllegalStateException("Account with current id cannot be found: "+developerId));

        Supports newSupport = new Supports();          
        newSupport.setTicket(ticket);
        newSupport.setDateTaken(OffsetDateTime.now());
        newSupport.setDeveloper(developer);
        newSupport.setIsActive(true);

        return supportRepository.save(newSupport);
    }

}
