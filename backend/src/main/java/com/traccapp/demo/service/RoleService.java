package com.traccapp.demo.service;

import java.util.List;

import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Roles;
import com.traccapp.demo.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    
    @Autowired
    private final RoleRepository roleRepository;

    public List<Roles> getAllRoles(){
        return roleRepository.findAll();
    }

    public Roles getRole(ERoles name){
        return roleRepository.findByName(name)
            .orElseThrow(() -> new AbstractGraphQLException("Role with current name cannot be found"+name.toString(),"roleName"));
           
    }
}
