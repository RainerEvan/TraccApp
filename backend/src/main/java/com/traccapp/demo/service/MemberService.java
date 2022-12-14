package com.traccapp.demo.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Members;
import com.traccapp.demo.model.Teams;
import com.traccapp.demo.payload.request.MemberRequest;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.MemberRepository;
import com.traccapp.demo.repository.TeamRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final AccountRepository accountRepository;

    @Transactional
    public List<Members> getAllMembersForTeam(UUID teamId){
        Teams team = teamRepository.findById(teamId)
            .orElseThrow(() -> new AbstractGraphQLException("Team with current id cannot be found: "+teamId,"teamId"));

        return memberRepository.findAllByTeam(team);
    }

    @Transactional
    public Members getFirstMemberForDeveloper(UUID accountId){
        Accounts developer = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Developer with current id cannot be found: "+accountId,"accountId"));

        return memberRepository.findFirstByDeveloper(developer);
    }

    @Transactional
    public Members getMember(UUID memberId){
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new AbstractGraphQLException("Member with current id cannot be found: "+memberId,"memberId"));
    }

    @Transactional
    public Members addMember(MemberRequest memberRequest){
        Teams team = teamRepository.findById(memberRequest.getTeamId())
            .orElseThrow(() -> new IllegalStateException("Team with current id cannot be found: "+memberRequest.getTeamId()));
        
        Accounts developer = accountRepository.findById(memberRequest.getDeveloperId())
            .orElseThrow(() -> new IllegalStateException("Account with current id cannot be found: "+memberRequest.getDeveloperId()));

        Members member = new Members();
        member.setTeam(team);
        member.setDeveloper(developer);

        return memberRepository.save(member);
    }

    @Transactional
    public void deletemember(UUID memberId){
        memberRepository.delete(getMember(memberId));
    }
}