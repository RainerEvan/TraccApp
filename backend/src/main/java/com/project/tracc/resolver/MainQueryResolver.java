package com.project.tracc.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Applications;
import com.project.tracc.model.Divisions;
import com.project.tracc.model.Roles;
import com.project.tracc.model.Tags;
import com.project.tracc.service.ApplicationService;
import com.project.tracc.service.DivisionService;
import com.project.tracc.service.RoleService;
import com.project.tracc.service.TagsService;

import graphql.kickstart.tools.GraphQLQueryResolver;
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
