package com.traccapp.demo.repository;

import java.util.UUID;

import com.traccapp.demo.model.Comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {
    
}
