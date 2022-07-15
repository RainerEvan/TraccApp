package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.Comments;
import com.traccapp.demo.payload.response.ResponseHandler;
import com.traccapp.demo.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    
    @Autowired
    private final CommentService commentService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addComment(@RequestParam("ticketId") UUID ticketId, @RequestBody String body){
        try {
            Comments comment = commentService.addComment(ticketId,body);
        
            return ResponseHandler.generateResponse("Comment has been added successfully!", HttpStatus.OK, comment.getCreatedAt());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Object> editComment(@RequestParam("commentId") UUID commentId, @RequestBody String body){
        try {
            Comments comment = commentService.editComment(commentId, body);
        
            return ResponseHandler.generateResponse("Comment has been updated successfully!", HttpStatus.OK, comment.getUpdatedAt());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/delete")
    public ResponseEntity<Object> deleteComment(@RequestParam("commentId") UUID commentId){
        try {
            commentService.deleteComment(commentId);
        
            return ResponseHandler.generateResponse("Comment has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
