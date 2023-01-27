package com.project.tracc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopApplicationsResponse {
    private String application;
    private long count;
}
