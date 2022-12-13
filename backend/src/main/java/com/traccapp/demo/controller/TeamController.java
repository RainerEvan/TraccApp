package com.traccapp.demo.controller;

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

import com.traccapp.demo.model.Teams;
import com.traccapp.demo.service.TeamService;
import com.traccapp.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {
    
    @Autowired
    private final TeamService teamService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addTeam(@RequestBody String name){
        try{
            Teams team = teamService.addTeam(name);

            return ResponseHandler.generateResponse("Team has been added successfully!", HttpStatus.OK, team.getName());
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteTeam(@RequestParam("teamId") UUID teamId){
        try {
            teamService.deleteTeam(teamId);
            
            return ResponseHandler.generateResponse("Team has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
