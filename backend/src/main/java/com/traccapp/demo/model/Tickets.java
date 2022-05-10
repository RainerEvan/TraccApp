package com.traccapp.demo.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
public class Tickets {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String ticketNo;

    @ManyToOne
    @JoinColumn(name="application_id")
    private Applications application;

    private LocalDate dateAdded;

    @ManyToOne
    @JoinColumn(name="reporter_id")
    private Accounts reporter;

    private String title;
    private String description;
    private LocalDate dateClosed;

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "tickets_tags",
                joinColumns = @JoinColumn(name = "tickets_id"),
                inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Set<Tags> tags = new HashSet<>();

    public Tickets(String ticketNo, Applications application, LocalDate dateAdded, Accounts reporter, String title,
            String description, LocalDate dateClosed, Status status) {
        this.ticketNo = ticketNo;
        this.application = application;
        this.dateAdded = dateAdded;
        this.reporter = reporter;
        this.title = title;
        this.description = description;
        this.dateClosed = dateClosed;
        this.status = status;
    }

}
