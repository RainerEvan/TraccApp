package com.traccapp.demo.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Teams;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.TeamRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeamService {
    
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final AuthService authService;

    @Transactional
    public List<Teams> getAllTeamsForSupervisor(UUID accountId){
        Accounts supervisor = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return teamRepository.findAllBySupervisor(supervisor);
    }

    @Transactional
    public Teams getTeam(UUID teamId){
        return teamRepository.findById(teamId)
            .orElseThrow(() -> new AbstractGraphQLException("Team with current id cannot be found: "+teamId,"teamId"));
    }

    @Transactional
    public Teams addTeam(String name){
        Teams team = new Teams();
        team.setName(name);
        team.setSupervisor(authService.getCurrentAccount());

        return teamRepository.save(team);
    }

    @Transactional
    public void deleteTeam(UUID teamId){
        teamRepository.delete(getTeam(teamId));
    }
}
