package com.project.tracc.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Divisions;
import com.project.tracc.repository.DivisionRepository;

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
            throw new IllegalStateException("Division with current name already exists: "+name);
        }

        Divisions division = new Divisions();
        division.setName(name);
        return divisionRepository.save(division);
    }

    public void deleteDivision(UUID divisionId){
        Divisions division = divisionRepository.findById(divisionId)
            .orElseThrow(() -> new IllegalStateException("Division with current id cannot be found: "+divisionId));

        divisionRepository.delete(division);
    }
}
