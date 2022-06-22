package com.traccapp.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.TicketAttachments;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.request.SupportRequest;
import com.traccapp.demo.payload.request.TicketRequest;
import com.traccapp.demo.service.SupportAttachmentService;
import com.traccapp.demo.service.SupportService;
import com.traccapp.demo.service.TicketAttachmentService;
import com.traccapp.demo.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketSupportController {
    
    @Autowired
    private final TicketService ticketService;
    @Autowired
    private final TicketAttachmentService ticketAttachmentService;
    @Autowired
    private final SupportService supportService;
    @Autowired
    private final SupportAttachmentService supportAttachmentService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addTicket(@RequestPart(name="files", required = false) MultipartFile[] files, @RequestPart("ticket") TicketRequest ticketRequest){
        Tickets ticket = ticketService.addTicket(ticketRequest);

        if(files != null){
            Arrays.asList(files).stream()
                .map(file -> ticketAttachmentService.addFile(file, ticket))
                .collect(Collectors.toList());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Ticket has been created successfully: "+ticket.getTicketNo());
    }

    @PutMapping(path = "/close")
    public ResponseEntity<String> closeTicket(@RequestParam("ticketId") UUID ticketId){
        updateTicketStatus(ticketId, EStatus.CLOSED);

        return ResponseEntity.status(HttpStatus.OK).body("Ticket has been closed!");
    }

    @PutMapping(path = "/drop")
    public ResponseEntity<String> dropTicket(@RequestParam("ticketId") UUID ticketId){
        updateTicketStatus(ticketId, EStatus.DROPPED);

        return ResponseEntity.status(HttpStatus.OK).body("Ticket has been dropped!");
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<String> cancelTicket(@RequestParam("ticketId") UUID ticketId){
        Tickets ticket = ticketService.getTicket(ticketId);

        List<TicketAttachments> files = ticketAttachmentService.getAllFilesForTicket(ticket);

        for(TicketAttachments file:files){
            ticketAttachmentService.deleteFile(file.getId());
        }

        ticketService.deleteTicket(ticketId);

        return ResponseEntity.status(HttpStatus.OK).body("Ticket has been canceled!");
    }

    @PostMapping(path = "/supports/add")
    public ResponseEntity<String> addSupport(@RequestParam("ticketId") UUID ticketId){
        Supports support = supportService.addSupport(ticketId);

        updateTicketStatus(support.getTicket().getTicketId(), EStatus.IN_PROGRESS);

        return ResponseEntity.status(HttpStatus.OK).body("Support has been added successfully: "+support.getDateTaken());
    }

    @PostMapping(path = "/supports/reassign")
    public ResponseEntity<String> reassignSupport(@RequestParam("ticketId") UUID ticketId, @RequestParam("currSupportId") UUID currSupportId, @RequestParam("developerId") UUID developerId){
        Supports support = supportService.reassignSupport(ticketId, currSupportId, developerId);

        updateTicketStatus(support.getTicket().getTicketId(), EStatus.IN_PROGRESS);

        return ResponseEntity.status(HttpStatus.OK).body("New support has been added successfully: "+support.getDateTaken());
    }

    @PutMapping(path = "/supports/solve")
    public ResponseEntity<String> solveSupport(@RequestPart("files") MultipartFile[] files, @RequestPart("support") SupportRequest supportRequest){
        Supports support = supportService.solveSupport(supportRequest);

        if(files != null){
            Arrays.asList(files).stream()
                .map(file -> supportAttachmentService.addFile(file, support))
                .collect(Collectors.toList());
        }

        updateTicketStatus(support.getTicket().getTicketId(), EStatus.RESOLVED);

        return ResponseEntity.status(HttpStatus.OK).body("Support has been updated succesfully!");
    }

    //withdraw Support
    // public Supports withdrawSupport(UUID supportId, String result, String description){
    //     return supportService.withdrawSupport(supportId, result, description);
    // }

    public ResponseEntity<String> updateTicketStatus(UUID ticketId, EStatus status){
        Tickets ticket = ticketService.updateTicketStatus(ticketId, status);

        return ResponseEntity.status(HttpStatus.OK).body("Ticket status has been updated: "+ticket.getStatus().getName());
    }
    
}
