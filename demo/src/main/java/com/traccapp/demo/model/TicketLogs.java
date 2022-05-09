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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "ticket_logs")
public class TicketLogs {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Tickets ticket;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Accounts account;

    private String action;
    private LocalDateTime dateTime;

    public TicketLogs(Tickets ticket, Accounts account, String action, LocalDateTime dateTime) {
        this.ticket = ticket;
        this.account = account;
        this.action = action;
        this.dateTime = dateTime;
    }

}
