package com.project.tracc.resolver.comments;

import java.util.List;
import java.util.UUID;

import graphql.kickstart.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.model.Comments;
import com.project.tracc.service.CommentService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommentsQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final CommentService commentService;

    public List<Comments> getAllCommentsForTicket(UUID ticketId){
        return commentService.getAllCommentsForTicket(ticketId);
    }


}
