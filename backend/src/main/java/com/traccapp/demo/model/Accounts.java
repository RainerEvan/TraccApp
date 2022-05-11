package com.traccapp.demo.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
    private String email;
    private String displayName;
    private String contactNo;

    @ManyToOne
    @JoinColumn(name="division_id")
    private Divisions division;

    @Lob
    private String profileImg;
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "account_role",
                joinColumns = @JoinColumn(name = "account_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> role = new HashSet<>();

    public Accounts(String username, String password, String email, String displayName, String contactNo,
            Divisions division, String profileImg, Boolean isActive, Set<Roles> role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.contactNo = contactNo;
        this.division = division;
        this.profileImg = profileImg;
        this.isActive = isActive;
        this.role = role;
    }

    
}
