package com.traccapp.demo.resolver.comments;

import java.util.List;
import java.util.UUID;

import com.traccapp.demo.model.Comments;
import com.traccapp.demo.service.CommentService;

import graphql.kickstart.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
