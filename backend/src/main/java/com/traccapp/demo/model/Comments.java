package com.traccapp.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "t_comments")
public class Comments {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Tickets ticket;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Accounts author;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private Boolean isDeleted = false;

    public Comments(Tickets ticket, Accounts author, LocalDateTime createdAt, LocalDateTime updatedAt, String body) {
        this.ticket = ticket;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.body = body;
    }

}
