package com.project.tracc.service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Accounts;
import com.project.tracc.model.Supports;
import com.project.tracc.model.Tags;
import com.project.tracc.model.Tickets;
import com.project.tracc.payload.request.AssignSupportRequest;
import com.project.tracc.payload.request.ReassignSupportRequest;
import com.project.tracc.payload.request.SupportRequest;
import com.project.tracc.repository.AccountRepository;
import com.project.tracc.repository.SupportRepository;
import com.project.tracc.repository.TicketRepository;

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
    private final TagsService tagsService;

    @Transactional
    public Supports getSupport(UUID supportId){
        return supportRepository.findById(supportId)
            .orElseThrow(() -> new AbstractGraphQLException("Support with current id cannot be found: "+supportId, "supportId"));
    }
    
    @Transactional
    public Supports getSupportForTicket(Tickets ticket){
        return supportRepository.findFirstByTicketAndIsActive(ticket, true);
    }

    @Transactional
    public List<Supports> getAllSupportsForDeveloper(UUID accountId){
        Accounts developer = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));
        
        return supportRepository.findAllByDeveloper(developer);
    }

    @Transactional
    public Supports addSupport(AssignSupportRequest assignSupportRequest) {
        Tickets ticket = ticketRepository.findByTicketId(assignSupportRequest.getTicketId())
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+assignSupportRequest.getTicketId()));

        Accounts developer = accountRepository.findById(assignSupportRequest.getAccountId())
            .orElseThrow(() -> new IllegalStateException("Account with current id cannot be found: "+assignSupportRequest.getAccountId()));

        Supports support = new Supports();        
        support.setTicket(ticket);
        support.setDateTaken(OffsetDateTime.now());
        support.setDeveloper(developer);
        support.setIsActive(true);

        return supportRepository.save(support);
    }

    @Transactional
    public Supports solveSupport(UUID supportId, SupportRequest supportRequest) {
        
        Supports support = supportRepository.findById(supportId)
            .orElseThrow(() -> new IllegalStateException("Support with current id cannot be found: "+supportId));

        support.setResult(supportRequest.getResult());
        support.setDescription(supportRequest.getDescription());
        support.setDevNote(supportRequest.getDevNote());

        Set<String> tags = supportRequest.getTagsName();
        
        Set<Tags> tagSet = new HashSet<>();
        
        for(String tag: tags){
            Tags currTag = tagsService.getTag(tag);
            
            tagSet.add(currTag);
        }

        support.setTags(tagSet);

        return supportRepository.save(support);
    }

    @Transactional
    public Supports requestDropSupport(UUID supportId, SupportRequest supportRequest){
        Supports support = supportRepository.findById(supportId)
            .orElseThrow(() -> new IllegalStateException("Support with current id cannot be found: "+supportId));

        support.setResult(supportRequest.getResult());
        support.setDescription(supportRequest.getDescription());
        support.setDevNote(supportRequest.getDevNote());

        return supportRepository.save(support);
    }

    @Transactional
    public Supports reassignSupport(ReassignSupportRequest reassignSupportRequest){
        Tickets ticket = ticketRepository.findByTicketId(reassignSupportRequest.getTicketId())
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+reassignSupportRequest.getTicketId()));

        Accounts developer = accountRepository.findById(reassignSupportRequest.getDeveloperId())
            .orElseThrow(() -> new IllegalStateException("Account with current id cannot be found: "+reassignSupportRequest.getDeveloperId()));

        Supports newSupport = new Supports();          
        newSupport.setTicket(ticket);
        newSupport.setDateTaken(OffsetDateTime.now());
        newSupport.setDeveloper(developer);
        newSupport.setIsActive(true);

        return supportRepository.save(newSupport);
    }

    @Transactional
    public void deactivateSupport(UUID supportId){
        Supports currSupport = supportRepository.findById(supportId)
            .orElseThrow(() -> new IllegalStateException("Support with current id cannot be found: "+supportId));
            
        currSupport.setIsActive(false);
        currSupport.setDateReassigned(OffsetDateTime.now());
        supportRepository.save(currSupport);
    }

}
