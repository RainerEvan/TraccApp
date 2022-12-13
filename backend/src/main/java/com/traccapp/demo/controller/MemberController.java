package com.traccapp.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.payload.request.MemberRequest;
import com.traccapp.demo.service.MemberService;
import com.traccapp.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addMember(@RequestBody MemberRequest memberRequest){
        try{
            List<UUID> developersId = memberRequest.getDevelopersId();

            developersId.stream().map(developerId -> memberService.addMember(memberRequest.getTeamId(), developerId));

            return ResponseHandler.generateResponse("Member has been added successfully!", HttpStatus.OK, developersId.size());
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deletemember(@RequestParam("memberId") UUID memberId){
        try {
            memberService.deletemember(memberId);
            
            return ResponseHandler.generateResponse("Member has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
