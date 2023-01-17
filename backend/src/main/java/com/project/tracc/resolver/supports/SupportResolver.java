package com.project.tracc.resolver.supports;

import java.util.List;

import graphql.kickstart.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.SupportAttachments;
import com.project.tracc.model.Supports;
import com.project.tracc.service.SupportAttachmentService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SupportResolver implements GraphQLResolver<Supports> {
    
    @Autowired
    private final SupportAttachmentService supportAttachmentService;

    public List<SupportAttachments> getAttachments(Supports support){
        return supportAttachmentService.getAllFilesForsupport(support);
    }
}
