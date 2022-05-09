package com.traccapp.demo.model;

import java.time.LocalDate;
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
public class Supports {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="ticket_id")
    private Tickets ticket;

    private LocalDate dateTaken;

    @ManyToOne
    @JoinColumn(name="developer_id")
    private Accounts developer;

    private String result;
    private String description;
    private String devNote;
    private Boolean isResolved;
    
    public Supports(Tickets ticket, LocalDate dateTaken, Accounts developer, String result, String description,
            String devNote, Boolean isResolved) {
        this.ticket = ticket;
        this.dateTaken = dateTaken;
        this.developer = developer;
        this.result = result;
        this.description = description;
        this.devNote = devNote;
        this.isResolved = isResolved;
    }

    
}
