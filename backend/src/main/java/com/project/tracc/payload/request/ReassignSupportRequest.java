package com.project.tracc.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReassignSupportRequest {
    private UUID ticketId;
    private UUID currSupportId;
    private UUID developerId;
}
