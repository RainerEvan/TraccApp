package com.project.tracc.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    private UUID accountId;
    private UUID applicationId;
    private String title;
    private String description;
}
