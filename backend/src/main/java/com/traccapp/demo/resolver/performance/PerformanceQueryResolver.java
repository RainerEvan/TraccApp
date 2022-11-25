package com.traccapp.demo.resolver.performance;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.traccapp.demo.payload.response.PerformanceResponse;
import com.traccapp.demo.service.PerformanceService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PerformanceQueryResolver implements GraphQLQueryResolver{
    @Autowired
    private final PerformanceService performanceService;

    public List<PerformanceResponse> getPerformanceForDeveloper(UUID accountId){
        return performanceService.getPerformanceForDeveloper(accountId);
    }
}
