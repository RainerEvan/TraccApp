package com.project.tracc.payload.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Date expirationDate;
    private UUID accountId;
    private String username;
    private boolean isActive;
    private List<String> roles;
}
