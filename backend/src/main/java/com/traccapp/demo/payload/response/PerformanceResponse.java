package com.traccapp.demo.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceResponse {
    private String menu;
    private String period;
    private int totalTickets;
    private String rate;
    private List<String> label;
    private List<Integer> data;
}
