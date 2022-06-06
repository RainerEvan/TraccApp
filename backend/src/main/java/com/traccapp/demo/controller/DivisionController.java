package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.service.DivisionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/divisions")
@AllArgsConstructor
public class DivisionController {
    
    @Autowired
    private final DivisionService divisionService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addDivision(@RequestParam("division") String name){
        Divisions division = divisionService.addDivision(name);
        
        return ResponseEntity.status(HttpStatus.OK).body("Division has been added successfully: "+division.getName());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteDivision(@RequestParam("divisionId") UUID divisionId){
        divisionService.deleteDivision(divisionId);

        return ResponseEntity.status(HttpStatus.OK).body("Division has been deleted!");
    }
}
