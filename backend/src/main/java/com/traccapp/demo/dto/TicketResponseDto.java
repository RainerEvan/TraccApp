package com.traccapp.demo.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.traccapp.demo.model.Comments;
import com.traccapp.demo.model.SupportAttachments;
import com.traccapp.demo.model.TicketAttachments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
    
    private UUID ticketId;
    private String ticketNo;
    private String application;

    private LocalDate dateAdded;
    private String user;
    private String title;
    private String reportDesc;
    private LocalDate dateClosed;
    private String status;
    private Set<TicketAttachments> ticketAttachments;

    private LocalDate dateTaken;
    private String developer;
    private String result;
    private String supportDesc;
    private String devNote;
    private Set<String> tags;
    private Set<SupportAttachments> supportAttachments;
  
    private Set<Comments> comments;
}
