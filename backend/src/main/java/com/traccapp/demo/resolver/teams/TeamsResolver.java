package com.traccapp.demo.resolver.teams;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traccapp.demo.model.Members;
import com.traccapp.demo.model.Teams;
import com.traccapp.demo.service.MemberService;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TeamsResolver implements GraphQLResolver<Teams>{
    
    @Autowired
    private final MemberService memberService;

    public List<Members> getMembers(Teams team){
        return memberService.getAllMembersForTeam(team.getId());
    }
}
