package com.traccapp.demo.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

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
    
    @Transactional
    public List<Tags> getAllTags(){
        return tagsRepository.findAll();
    }

    @Transactional
    public Tags getTag(String name){
        return tagsRepository.findByName(name)
            .orElseThrow(() -> new AbstractGraphQLException("Tags with current name cannot be found: "+name,"tagName"));
    }

    @Transactional
    public Tags getTag(UUID tagId){
        return tagsRepository.findById(tagId)
            .orElseThrow(() -> new AbstractGraphQLException("Tags with current id cannot be found: "+tagId,"tagId"));
    }

    @Transactional
    public Tags addTag(String name){

        if(tagsRepository.existsByName(name)){
            throw new IllegalStateException("Tags with current name already exists: "+name);
        }

        Tags tag = new Tags();
        tag.setName(name);
        return tagsRepository.save(tag);
    }

    @Transactional
    public void deleteTags(UUID tagId){
        Tags tag = tagsRepository.findById(tagId)
            .orElseThrow(() -> new IllegalStateException("Tag with current id cannot be found: "+tagId));

        tagsRepository.delete(tag);
    }
}
