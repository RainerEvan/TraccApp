package com.project.tracc.payload.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportRequest {
    private String result;
    private String description;
    private String devNote;
    private Set<String> tagsName;
}
