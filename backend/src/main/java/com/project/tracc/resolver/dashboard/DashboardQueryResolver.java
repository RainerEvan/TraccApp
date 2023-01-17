package com.project.tracc.resolver.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.tracc.payload.response.DashboardActivityResponse;
import com.project.tracc.payload.response.DashboardAnalyticsResponse;
import com.project.tracc.service.DashboardService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DashboardQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final DashboardService dashboardService;

    public List<DashboardActivityResponse> getDashboardActivity(){
        return dashboardService.getDashboardActivity();
    }

    public List<DashboardAnalyticsResponse> getDashboardAnalytics(){
        return dashboardService.getDashboardAnalytics();
    }
}
