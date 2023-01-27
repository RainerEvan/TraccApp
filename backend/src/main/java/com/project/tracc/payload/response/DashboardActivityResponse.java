package com.project.tracc.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardActivityResponse {
    private String menu;
    private String period;
    private int totalPending;
    private int totalInProgress;
    private int totalResolved;
    private int totalDropped;
    private int totalTickets;
    private List<String> label;
    private List<Integer> data;
}
