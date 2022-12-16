package com.traccapp.demo.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequest {
    private UUID accountId;
    private UUID ticketId;
    private String body;
}
