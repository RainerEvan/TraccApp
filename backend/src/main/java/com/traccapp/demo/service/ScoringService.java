package com.traccapp.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.model.Scorings;
import com.traccapp.demo.payload.request.ScoringRequest;
import com.traccapp.demo.repository.ScoringRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScoringService {
    
    @Autowired
    private final ScoringRepository scoringRepository;

    public Scorings getScoring(){
        return scoringRepository.findAll().get(0);
    }

    public Scorings addScoring(ScoringRequest scoringRequest){
        Scorings scoring = new Scorings();
        scoring.setTicketPoint(scoringRequest.getTicketPoint());
        scoring.setTicketSLA(scoringRequest.getTicketSLA());
        scoring.setMaxTicketSLA(scoringRequest.getMaxTicketSLA());
        scoring.setTicketReassignedDeduction(scoringRequest.getTicketReassignedDeduction());

        return scoringRepository.save(scoring);
    }

    public Scorings editScoring(UUID scoringId, ScoringRequest scoringRequest){
        Scorings scoring = scoringRepository.findById(scoringId)
            .orElseThrow(() -> new IllegalStateException("Scoring with current id cannot be found: "+scoringId));

        int ticketPoint = scoringRequest.getTicketPoint();
        int ticketSLA = scoringRequest.getTicketSLA();
        int maxTicketSLA = scoringRequest.getMaxTicketSLA();
        int ticketReassignedDeduction = scoringRequest.getTicketReassignedDeduction();

        if(ticketPoint != 0 && ticketPoint != scoring.getTicketPoint()){
            scoring.setTicketPoint(ticketPoint);
        }

        if(ticketSLA != 0 && ticketSLA != scoring.getTicketSLA()){
            scoring.setTicketSLA(ticketSLA);
        }

        if(maxTicketSLA != 0 && maxTicketSLA != scoring.getMaxTicketSLA()){
            scoring.setMaxTicketSLA(maxTicketSLA);
        }

        if(ticketReassignedDeduction != 0 && ticketReassignedDeduction != scoring.getTicketReassignedDeduction()){
            scoring.setTicketReassignedDeduction(ticketReassignedDeduction);
        }

        return scoringRepository.save(scoring);
    }
}
