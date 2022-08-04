package com.traccapp.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRateResponse {
    private String label;
    private double rate;
}
