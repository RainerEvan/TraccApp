package com.project.tracc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopTagsResponse {
    private String tag;
    private long count;
}
