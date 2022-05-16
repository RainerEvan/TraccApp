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
@Table(name = "t_ticket_logs")
public class TicketLogs {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Tickets ticket;

    @ManyToOne
    @JoinColumn(name="support_id")
    private Supports support;

    @ManyToOne
    @JoinColumn(name="actor_id")
    private Accounts actor;

    private String action;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    public TicketLogs(Tickets ticket, Supports support, Accounts actor, String action, LocalDateTime dateTime,
            Status status) {
        this.ticket = ticket;
        this.support = support;
        this.actor = actor;
        this.action = action;
        this.dateTime = dateTime;
        this.status = status;
    }

    
}