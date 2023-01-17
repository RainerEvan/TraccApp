package com.project.tracc.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoringRequest {
    private int ticketPoint;
    private int ticketSLA;
    private int maxTicketSLA;
    private int ticketReassignedDeduction;
}
