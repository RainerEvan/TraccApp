package com.traccapp.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    private LocalDateTime postedAt;
    private LocalDateTime updatedAt;
    private String body;
    private Boolean isDeleted;

    public Comments(Tickets ticket, Accounts author, LocalDateTime postedAt, LocalDateTime updatedAt, String body,
            Boolean isDeleted) {
        this.ticket = ticket;
        this.author = author;
        this.postedAt = postedAt;
        this.updatedAt = updatedAt;
        this.body = body;
        this.isDeleted = isDeleted;
    }

}
