package com.traccapp.demo.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.exception.FileUploadException;
import com.traccapp.demo.model.TicketAttachments;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.repository.TicketAttachmentRepository;
import com.traccapp.demo.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketAttachmentService{

    @Autowired
    private final TicketAttachmentRepository ticketAttachmentRepository;
    @Autowired
    private final TicketRepository ticketRepository;

    public List<TicketAttachments> getAllFilesForTicket(Tickets ticket){
        return ticketAttachmentRepository.findAllByTicket(ticket);
    }

    public TicketAttachments getFile(UUID attachmentId){
        return ticketAttachmentRepository.findById(attachmentId)
            .orElseThrow(() -> new AbstractGraphQLException("Attachment with current id cannot be found: "+attachmentId,"attachmentId"));
    }

    public TicketAttachments addFile(MultipartFile file, UUID ticketId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{

            Tickets ticket = ticketRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new AbstractGraphQLException("Ticket with current id cannot be found: "+ticketId, "ticketId"));

            if(fileName.contains("..")){
                throw new FileUploadException("Filename contains invalid path sequence: " + fileName);
            }

            TicketAttachments ticketAttachments = new TicketAttachments();
            ticketAttachments.setTicket(ticket);
            ticketAttachments.setFileName(fileName);
            ticketAttachments.setFileType(file.getContentType());
            ticketAttachments.setFileSize(file.getSize());

            String encodedString = Base64.getEncoder().encodeToString(file.getBytes());
            ticketAttachments.setFileBase64(encodedString);

            return ticketAttachmentRepository.save(ticketAttachments);
            
        } catch (IOException exception){
            throw new FileUploadException("Could not add the current file: "+fileName, exception);
        }
    }
    
    
}
