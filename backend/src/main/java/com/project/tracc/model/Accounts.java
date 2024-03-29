package com.project.tracc.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.JoinColumn;
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
@Table(name = "m_accounts")
public class Accounts {
     
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String username;
    private String password;
    private String fullname;
    private String email;
    private String contactNo;

    @ManyToOne
    @JoinColumn(name="division_id")
    private Divisions division;

    @Lob
    private String profileImg;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Roles role;
}
