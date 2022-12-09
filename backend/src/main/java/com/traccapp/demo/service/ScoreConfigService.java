package com.traccapp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.model.ScoreConfigs;
import com.traccapp.demo.payload.request.ScoreConfigRequest;
import com.traccapp.demo.repository.ScoreConfigRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScoreConfigService {
    
    @Autowired
    private final ScoreConfigRepository scoreConfigRepository;

    public ScoreConfigs getScoreConfig(){
        return scoreConfigRepository.findAll().get(0);
    }

    public ScoreConfigs addScoreConfig(ScoreConfigRequest scoreConfigRequest){
        ScoreConfigs scoreConfig = new ScoreConfigs();
        scoreConfig.setTicketPoint(scoreConfigRequest.getTicketPoint());
        scoreConfig.setTicketSLA(scoreConfigRequest.getTicketSLA());
        scoreConfig.setMaxTicketSLA(scoreConfigRequest.getMaxTicketSLA());
        scoreConfig.setTicketReassignedDeduction(scoreConfigRequest.getTicketReassignedDeduction());

        return scoreConfigRepository.save(scoreConfig);
    }
}
