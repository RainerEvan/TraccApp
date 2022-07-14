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

import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.payload.response.ResponseHandler;
import com.traccapp.demo.service.DivisionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/divisions")
@AllArgsConstructor
public class DivisionController {
    
    @Autowired
    private final DivisionService divisionService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addDivision(@RequestBody String name){
        try {
            Divisions division = divisionService.addDivision(name);
            
            return ResponseHandler.generateResponse("Division has been added successfully!", HttpStatus.OK, division.getName());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteDivision(@RequestParam("divisionId") UUID divisionId){
        try {
            divisionService.deleteDivision(divisionId);
            
            return ResponseHandler.generateResponse("Division has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
