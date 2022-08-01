package com.traccapp.demo.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardAnalyticsResponse {
    private String period;
    private int minTickets;
    private int maxTickets;
    private int avgTickets;
    private int totalTickets;
    private List<String> label;
    private List<Integer> data;
}
