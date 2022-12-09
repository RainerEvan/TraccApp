package com.traccapp.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreConfigRequest {
    private int ticketPoint;
    private int ticketSLA;
    private int maxTicketSLA;
    private int ticketReassignedDeduction;
}
