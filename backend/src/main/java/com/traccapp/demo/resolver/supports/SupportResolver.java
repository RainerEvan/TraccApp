package com.traccapp.demo.resolver.supports;

import java.util.List;

import com.traccapp.demo.model.SupportAttachments;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.service.SupportAttachmentService;

import graphql.kickstart.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
