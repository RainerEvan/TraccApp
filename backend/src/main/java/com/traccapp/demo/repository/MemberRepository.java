package com.traccapp.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Members;
import com.traccapp.demo.model.Teams;

@Repository
public interface MemberRepository extends JpaRepository<Members,UUID>{
    Members findFirstByDeveloper(Accounts developer);
    List<Members> findAllByTeam(Teams team);
}
