package com.project.tracc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRateResponse {
    private String label;
    private String rate;
}
