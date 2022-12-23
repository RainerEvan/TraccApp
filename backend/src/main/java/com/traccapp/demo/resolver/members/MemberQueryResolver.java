package com.traccapp.demo.resolver.members;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traccapp.demo.model.Members;
import com.traccapp.demo.service.MemberService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MemberQueryResolver implements GraphQLQueryResolver{

    @Autowired
    private final MemberService memberService;

    @Transactional
    public List<Members> getAllMembersForTeam(UUID teamId){
        return memberService.getAllMembersForTeam(teamId);
    }

    @Transactional
    public Members getFirstMemberForDeveloper(UUID accountId){
        return memberService.getFirstMemberForDeveloper(accountId);
    }

    @Transactional
    public Members getMember(UUID memberId){
        return memberService.getMember(memberId);
    }
}
