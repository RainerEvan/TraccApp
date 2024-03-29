package com.project.tracc.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Accounts;
import com.project.tracc.model.Comments;
import com.project.tracc.model.Tickets;
import com.project.tracc.payload.request.CommentRequest;
import com.project.tracc.repository.AccountRepository;
import com.project.tracc.repository.CommentRepository;
import com.project.tracc.repository.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TicketRepository ticketRepository;

    @Transactional
    public List<Comments> getAllCommentsForTicket(UUID ticketId){

        Tickets ticket = ticketRepository.findByTicketId(ticketId)
            .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

        return commentRepository.findAllByTicketAndIsActive(ticket, true);
    }

    @Transactional
    public Comments addComment(CommentRequest commentRequest) {
        Accounts account = accountRepository.findById(commentRequest.getAccountId())
            .orElseThrow(() -> new IllegalStateException("Account with current id cannot be found: "+commentRequest.getAccountId()));

        Tickets ticket = ticketRepository.findByTicketId(commentRequest.getTicketId())
            .orElseThrow(() -> new IllegalStateException("Ticket with current id cannot be found: "+commentRequest.getTicketId()));

        Comments comment = new Comments();
        comment.setTicket(ticket);
        comment.setAuthor(account);
        comment.setCreatedAt(OffsetDateTime.now());
        comment.setBody(commentRequest.getBody());
        comment.setIsActive(true);

        return commentRepository.save(comment);
    }

    @Transactional
    public Comments editComment(UUID commentId, String body){
        Comments comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalStateException("Comment with current id cannot be found"+commentId));
        comment.setBody(body);
        comment.setUpdatedAt(OffsetDateTime.now());

        return commentRepository.save(comment);
    }

    public void deleteComment(UUID commentId){

        Comments comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalStateException("Comment with current id cannot be found"+commentId));
        comment.setIsActive(false);

        commentRepository.save(comment);
    }
}
