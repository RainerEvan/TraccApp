package com.traccapp.demo.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "support_attachments")
public class SupportAttachments {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="support_id")
    private Supports support;

    @Lob
    private String fileBase64;
    private String fileName;
    private String fileType;

    public SupportAttachments(Supports support, String fileBase64, String fileName, String fileType) {
        this.support = support;
        this.fileBase64 = fileBase64;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
