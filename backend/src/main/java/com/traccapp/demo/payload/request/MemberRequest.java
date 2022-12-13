package com.traccapp.demo.payload.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRequest {
    private UUID teamId;
    private List<UUID> developersId;
}