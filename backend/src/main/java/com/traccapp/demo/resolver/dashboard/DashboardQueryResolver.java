package com.traccapp.demo.resolver.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.payload.response.DashboardActivityResponse;
import com.traccapp.demo.service.DashboardService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DashboardQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final DashboardService dashboardService;

    public List<DashboardActivityResponse> getDashboardActivity(){
        return dashboardService.getDashboardActivity();
    }
}
