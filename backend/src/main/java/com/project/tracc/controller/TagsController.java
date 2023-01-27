package com.project.tracc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tracc.model.Tags;
import com.project.tracc.service.TagsService;
import com.project.tracc.utils.ResponseHandler;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagsController {
    
    @Autowired
    private final TagsService tagsService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addTag(@RequestBody String name){
        try {
            Tags tag = tagsService.addTag(name);
        
            return ResponseHandler.generateResponse("Tag has been added successfully!", HttpStatus.OK, tag.getName());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteTag(@RequestParam("tagId") UUID tagId){
        try {
            tagsService.deleteTags(tagId);
            
            return ResponseHandler.generateResponse("Tag has been deleted successfully!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
}
