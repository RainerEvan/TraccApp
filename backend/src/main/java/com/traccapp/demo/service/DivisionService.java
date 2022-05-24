package com.traccapp.demo.service;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.repository.DivisionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DivisionService {
    
    @Autowired
    private final DivisionRepository divisionRepository;
    
    public List<Divisions> getAllDivisions(){
        return divisionRepository.findAll();
    }

    public Divisions getDivision(UUID divisionId){
        return divisionRepository.findById(divisionId)
            .orElseThrow(() -> new AbstractGraphQLException("Division with current id cannot be found: "+divisionId,"divisionId"));
    }

    public Divisions addDivision(String name){

        if(divisionRepository.existsByName(name)){
            throw new AbstractGraphQLException("Division with current name already exists: "+name,"divisionName");
        }

        Divisions division = new Divisions();
        division.setName(name);
        return divisionRepository.save(division);
    }

    public void deleteDivision(UUID divisionId){
        divisionRepository.delete(getDivision(divisionId));
    }
}
