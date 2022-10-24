package com.traccapp.demo.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.model.Roles;
import com.traccapp.demo.model.Tags;
import com.traccapp.demo.service.ApplicationService;
import com.traccapp.demo.service.DivisionService;
import com.traccapp.demo.service.RoleService;
import com.traccapp.demo.service.TagsService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MainQueryResolver implements GraphQLQueryResolver{
    @Autowired
    private final ApplicationService applicationService;
    @Autowired
    private final DivisionService divisionService;
    @Autowired
    private final TagsService tagsService;
    @Autowired
    private final RoleService roleService;

    public List<Applications> getAllApplications(){
        return applicationService.getAllApplications();
    }

    public List<Divisions> getAllDivisions(){
        return divisionService.getAllDivisions();
    }

    public List<Tags> getAllTags(){
        return tagsService.getAllTags();
    }

    public List<Roles> getAllRoles(){
        return roleService.getAllRoles();
    }
}
