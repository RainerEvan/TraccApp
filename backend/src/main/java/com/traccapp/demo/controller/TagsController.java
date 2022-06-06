package com.traccapp.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traccapp.demo.model.Tags;
import com.traccapp.demo.service.TagsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagsController {
    
    @Autowired
    private final TagsService tagsService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addTag(@RequestParam("tag") String name){
        Tags tag = tagsService.addTag(name);
        
        return ResponseEntity.status(HttpStatus.OK).body("Tag has been added successfully: "+tag.getName());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> deleteTag(@RequestParam("tagId") UUID tagId){
        tagsService.deleteTags(tagId);

        return ResponseEntity.status(HttpStatus.OK).body("Tag has been deleted!");
    }
}
