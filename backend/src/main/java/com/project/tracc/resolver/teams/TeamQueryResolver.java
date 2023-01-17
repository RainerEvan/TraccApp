package com.project.tracc.resolver.teams;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Teams;
import com.project.tracc.service.TeamService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TeamQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final TeamService teamService;

    @Transactional
    public List<Teams> getAllTeamsForSupervisor(UUID accountId){
        return teamService.getAllTeamsForSupervisor(accountId);
    }

    @Transactional
    public Teams getTeam(UUID teamId){
        return teamService.getTeam(teamId);
    }
}
