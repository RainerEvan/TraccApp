package com.traccapp.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.traccapp.demo.model.Tags;
import com.traccapp.demo.repository.TagsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagsService {
    
    @Autowired
    public TagsRepository tagsRepository;

    public List<Tags> getAllTags(){
        return tagsRepository.findAll();
    }

    public Optional<Tags> findById(UUID id){
        return tagsRepository.findById(id);
    }

    public void save(Tags tags){
        tagsRepository.save(tags);
    }

    public void delete(UUID id){
        tagsRepository.deleteById(id);
    }
}
