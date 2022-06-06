package com.traccapp.demo.payload.request;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportRequest {
    private UUID supportId;
    private String result;
    private String description;
    private String devNote;
    private Set<String> tags;
}
