package com.traccapp.demo.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

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
    private String profileImg;
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "account_role",
                joinColumns = @JoinColumn(name = "account_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Roles role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "account_division",
                joinColumns = @JoinColumn(name = "account_id"),
                inverseJoinColumns = @JoinColumn(name = "division_id"))
    private Divisions division;

    public Accounts(String username, String password, String email, String displayName, String contactNo,
            String profileImg, Boolean isActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.contactNo = contactNo;
        this.profileImg = profileImg;
        this.isActive = isActive;
    }
    
}
