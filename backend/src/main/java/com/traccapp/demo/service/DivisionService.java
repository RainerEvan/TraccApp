package com.traccapp.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.repository.DivisionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DivisionService {
    
    @Autowired
    public DivisionRepository divisionRepository;

    public List<Divisions> getAllDivisions(){
        return divisionRepository.findAll();
    }

    public Optional<Divisions> findById(UUID id){
        return divisionRepository.findById(id);
    }

    public void save(Divisions division){
        divisionRepository.save(division);
    }

    public void delete(UUID id){
        divisionRepository.deleteById(id);
    }
}
