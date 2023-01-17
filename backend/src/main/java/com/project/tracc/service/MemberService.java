package com.project.tracc.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Accounts;
import com.project.tracc.model.Members;
import com.project.tracc.model.Teams;
import com.project.tracc.payload.request.MemberRequest;
import com.project.tracc.repository.AccountRepository;
import com.project.tracc.repository.MemberRepository;
import com.project.tracc.repository.TeamRepository;

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
        Members member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalStateException("Member with current id cannot be found: "+memberId));

        memberRepository.delete(member);
    }
}