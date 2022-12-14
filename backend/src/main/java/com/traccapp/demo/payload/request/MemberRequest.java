package com.traccapp.demo.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRequest {
    private UUID teamId;
    private UUID developerId;
}