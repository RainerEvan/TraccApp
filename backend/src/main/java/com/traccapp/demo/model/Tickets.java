package com.traccapp.demo.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.traccapp.demo.utils.TicketNoGenerator;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "m_tickets")
@IdClass(TicketPK.class)
public class Tickets {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
    @GenericGenerator(
        name = "ticket_seq",
        strategy = "com.traccapp.demo.utils.TicketNoGenerator",
        parameters = {@Parameter(name = TicketNoGenerator.INCREMENT_PARAM, value = "1")}
    )
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

    @OneToMany(mappedBy = "ticket")
    private Set<Supports> support;

}
