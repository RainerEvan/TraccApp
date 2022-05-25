package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Comments;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.CommentRepository;
import com.traccapp.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final AuthService authService;

    public List<Comments> getAllCommentsForTicket(UUID ticketId){

        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

        return commentRepository.findAllByTicketAndIsActive(ticket, true);
    }

    public Comments addComment(UUID ticketId, String body) {

        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

        Comments comment = new Comments();
        comment.setTicket(ticket);
        comment.setAuthor(authService.getCurrentAccount());
        comment.setCreatedAt(OffsetDateTime.now());
        comment.setBody(body);
        comment.setIsActive(true);

        return commentRepository.save(comment);
    }

    public Comments editComment(UUID commentId, String body){

        Comments comment = commentRepository.findById(commentId).orElseThrow(() -> new AbstractGraphQLException("Comment with current id cannot be found"+commentId,"commentId"));
        comment.setBody(body);
        comment.setUpdatedAt(OffsetDateTime.now());

        return commentRepository.save(comment);
    }

    public void deleteComment(UUID commentId){

        Comments comment = commentRepository.findById(commentId).orElseThrow(() -> new AbstractGraphQLException("Comment with current id cannot be found"+commentId,"commentId"));
        comment.setIsActive(false);

        commentRepository.save(comment);
    }
}
