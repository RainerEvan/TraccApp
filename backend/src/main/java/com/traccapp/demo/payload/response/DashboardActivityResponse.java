package com.traccapp.demo.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardActivityResponse {
    private String timeframe;
    private int totalPending;
    private int totalInProgress;
    private int totalResolved;
    private int totalDropped;
    private int totalTickets;
    private List<String> label;
    private List<Integer> data;
}
