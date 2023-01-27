package com.project.tracc.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceResponse {
    private String menu;
    private String period;
    private int totalInProgress;
    private int totalResolved;
    private int totalDropped;
    private int totalReassigned;
    private int totalTickets;
    private String rate;
    private List<String> label;
    private List<Integer> data;
}
