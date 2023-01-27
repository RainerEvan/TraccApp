package com.project.tracc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tracc.model.Comments;
import com.project.tracc.model.Tickets;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {
    List<Comments> findAllByTicketAndIsActive(Tickets ticket, Boolean isActive);
}
