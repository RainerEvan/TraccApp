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
@Table(name = "t_notifications")
public class Notifications {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private Accounts sender;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    private Accounts receiver;

    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private String title;
    private String body;
    private String link;

    public Notifications(Accounts sender, Accounts receiver, LocalDateTime sentAt, LocalDateTime readAt, String title,
            String body, String link) {
        this.sender = sender;
        this.receiver = receiver;
        this.sentAt = sentAt;
        this.readAt = readAt;
        this.title = title;
        this.body = body;
        this.link = link;
    }

}
