package com.traccapp.demo.resolver.comments;

import java.util.UUID;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.traccapp.demo.model.Comments;
import com.traccapp.demo.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CommentsMutationResolver implements GraphQLMutationResolver{
    
    @Autowired
    private final CommentService commentService;

    public Comments addComment(UUID ticketId, String body){
        return commentService.addComment(ticketId,body);
    }

    public Comments ediComments(UUID commentId, String body){
        return commentService.editComment(commentId, body);
    }
}
