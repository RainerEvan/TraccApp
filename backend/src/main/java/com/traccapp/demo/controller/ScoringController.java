package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.Scorings;
import com.traccapp.demo.payload.request.ScoringRequest;
import com.traccapp.demo.service.ScoringService;
import com.traccapp.demo.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/scorings")
@AllArgsConstructor
public class ScoringController {
    @Autowired
    private final ScoringService scoringService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addScoring(@RequestBody ScoringRequest scoringRequest){
        try{
            Scorings scoring = scoringService.addScoring(scoringRequest);

            return ResponseHandler.generateResponse("Scoring has been added successfully!", HttpStatus.OK, scoring);
        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Object> editScoring(@RequestParam("scoringId") UUID scoringId, @RequestBody ScoringRequest scoringRequest){
        try {
            Scorings scoring = scoringService.editScoring(scoringId, scoringRequest);
            
            return ResponseHandler.generateResponse("Scoring has been updated successfully!", HttpStatus.OK, scoring);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
