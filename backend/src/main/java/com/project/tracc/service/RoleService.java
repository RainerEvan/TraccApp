package com.project.tracc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.model.Roles;
import com.project.tracc.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    
    @Autowired
    private final RoleRepository roleRepository;

    public List<Roles> getAllRoles(){
        return roleRepository.findAll();
    }
}
