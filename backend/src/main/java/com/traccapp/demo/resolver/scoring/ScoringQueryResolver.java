package com.traccapp.demo.resolver.scoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traccapp.demo.model.Scorings;
import com.traccapp.demo.service.ScoringService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScoringQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final ScoringService scoringService;

    public Scorings getScoring(){
        return scoringService.getScoring();
    }
}
