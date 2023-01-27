package com.project.tracc.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignSupportRequest {
    private UUID ticketId;
    private UUID accountId;
}
