package com.project.tracc.resolver.scoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Scorings;
import com.project.tracc.service.ScoringService;

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
