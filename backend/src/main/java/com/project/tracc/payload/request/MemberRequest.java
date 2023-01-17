package com.project.tracc.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRequest {
    private UUID teamId;
    private UUID developerId;
}