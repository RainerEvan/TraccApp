package com.traccapp.demo.dto;

import java.util.UUID;

import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    
    private UUID accountId;
    private String username;
    private String email;
    private String displayName;
    private String role;
    private String division;
    private String contactNo;
    @Lob
    private String profileImg;
    private Boolean isActive;
}
