package com.traccapp.demo.payload.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String tokem;
    private Date expirationDate;
    private String username;
}
