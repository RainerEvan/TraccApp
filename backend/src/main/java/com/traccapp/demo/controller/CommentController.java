package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    
    @Autowired
    private final CommentService commentService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addComment(@RequestParam("ticketId") UUID ticketId, @RequestParam("body") String body){
        commentService.addComment(ticketId,body);

        return ResponseEntity.status(HttpStatus.OK).body("Comment has been added successfully!");
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<String> editComment(@RequestParam("commentId") UUID commentId, @RequestParam("body") String body){
        commentService.editComment(commentId, body);

        return ResponseEntity.status(HttpStatus.OK).body("Comment has been updated successfully!");
    }

    @PutMapping(path = "/delete")
    public ResponseEntity<String> editComment(@RequestParam("commentId") UUID commentId){
        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body("Comment has been deleted successfully!");
    }
}
