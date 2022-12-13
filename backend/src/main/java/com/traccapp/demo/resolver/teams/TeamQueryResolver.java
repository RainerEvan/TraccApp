package com.traccapp.demo.resolver.teams;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.Teams;
import com.traccapp.demo.service.TeamService;

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
