package com.traccapp.demo.service;

import java.util.List;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Tags;
import com.traccapp.demo.repository.TagsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TagsService {
    
    @Autowired
    private final TagsRepository tagsRepository;
    
    public List<Tags> getAllTags(){
        return tagsRepository.findAll();
    }

    public Tags getTags(String name){
        return tagsRepository.findByName(name)
            .orElseThrow(() -> new AbstractGraphQLException("Tags with current name cannot be found: "+name,"tagName"));
    }

    public Tags addTags(String name){

        if(tagsRepository.existsByName(name)){
            throw new AbstractGraphQLException("Tags with current name already exists: "+name,"tagName");
        }

        Tags tags = new Tags();
        tags.setName(name);
        return tagsRepository.save(tags);
    }
}
