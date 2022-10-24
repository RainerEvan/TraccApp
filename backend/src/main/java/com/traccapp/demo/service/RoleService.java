package com.traccapp.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.model.Roles;
import com.traccapp.demo.repository.RoleRepository;

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
