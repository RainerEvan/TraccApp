package com.project.tracc.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "t_ticket_attachments")
public class TicketAttachments {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="ticket_id", referencedColumnName = "ticket_id"),
        @JoinColumn(name="ticket_no", referencedColumnName = "ticket_no")
    })
    private Tickets ticket;

    @Lob
    private String fileBase64;
    private String fileName;
    private String fileType;
    private Long fileSize;
}
