package com.project.tracc.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Tags;

@Repository
public interface TagsRepository extends JpaRepository<Tags,UUID>{
    Optional<Tags> findByName(String name);
    Boolean existsByName(String name);
}
