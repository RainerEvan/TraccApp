package com.traccapp.demo.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "m_supports")
public class Supports {
    
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

    private OffsetDateTime dateTaken;

    @ManyToOne
    @JoinColumn(name="developer_id")
    private Accounts developer;

    private String result;

    @Lob
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "supports_tags",
                joinColumns = @JoinColumn(name = "supports_id"),
                inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Set<Tags> tags = new HashSet<>();
    
    @Lob
    private String devNote;
    private Boolean isActive;
    
    @OneToMany(mappedBy = "support",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<SupportAttachments> attachments;
}
