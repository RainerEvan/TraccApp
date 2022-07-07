package com.traccapp.demo.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.traccapp.demo.config.TicketNoGenerator;

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
    @Column(name = "ticket_id")
    private UUID ticketId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq")
    @GenericGenerator(
        name = "ticket_seq",
        strategy = "com.traccapp.demo.config.TicketNoGenerator",
        parameters = {@Parameter(name = TicketNoGenerator.INCREMENT_PARAM, value = "50")}
    )
    @Column(name = "ticket_no")
    private String ticketNo;
  
    @ManyToOne
    @JoinColumn(name="application_id")
    private Applications application;

    private OffsetDateTime dateAdded;

    @ManyToOne
    @JoinColumn(name="reporter_id")
    private Accounts reporter;

    private String title;

    @Lob
    private String description;
    private OffsetDateTime dateClosed;

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private List<Supports> support;

    @OneToMany(mappedBy = "ticket",cascade = CascadeType.PERSIST,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<TicketAttachments> attachments;

}
