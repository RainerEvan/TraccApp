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
import com.traccapp.demo.utils.ResponseHandler;

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
    public ResponseEntity<Object> addTicket(@RequestPart(name="files", required = false) MultipartFile[] files, @RequestPart("ticket") TicketRequest ticketRequest){
        try{
            Tickets ticket = ticketService.addTicket(ticketRequest);

            if(files != null){
                Arrays.asList(files).stream()
                    .map(file -> ticketAttachmentService.addFile(file, ticket))
                    .collect(Collectors.toList());
            }

            return ResponseHandler.generateResponse("Ticket has been added successfully!", HttpStatus.OK, ticket.getTicketNo());

        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/close")
    public ResponseEntity<Object> closeTicket(@RequestParam("ticketId") UUID ticketId){
        try {
            String status = updateTicketStatus(ticketId, EStatus.CLOSED);

            return ResponseHandler.generateResponse("Ticket has been closed!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/drop")
    public ResponseEntity<Object> dropTicket(@RequestParam("ticketId") UUID ticketId){
        try {
            String status = updateTicketStatus(ticketId, EStatus.DROPPED);

            return ResponseHandler.generateResponse("Ticket has been dropped!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> cancelTicket(@RequestParam("ticketId") UUID ticketId){
        try {
            Tickets ticket = ticketService.getTicket(ticketId);
            ticketService.deleteTicket(ticketId);

            List<TicketAttachments> files = ticketAttachmentService.getAllFilesForTicket(ticket);

            for(TicketAttachments file:files){
                ticketAttachmentService.deleteFile(file.getId());
            }

            return ResponseHandler.generateResponse("Ticket has been canceled!", HttpStatus.OK, null);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/supports/add")
    public ResponseEntity<Object> addSupport(@RequestParam("ticketId") UUID ticketId){
        try {
            Supports support = supportService.addSupport(ticketId);
            String status = updateTicketStatus(support.getTicket().getTicketId(), EStatus.IN_PROGRESS);
            return ResponseHandler.generateResponse("Support has been added successfully!", HttpStatus.OK, status);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/supports/reassign")
    public ResponseEntity<Object> reassignSupport(@RequestParam("ticketId") UUID ticketId, @RequestParam("currSupportId") UUID currSupportId, @RequestParam("developerId") UUID developerId){
        try {
            Supports support = supportService.reassignSupport(ticketId, currSupportId, developerId);
            String status = updateTicketStatus(support.getTicket().getTicketId(), EStatus.IN_PROGRESS);
            return ResponseHandler.generateResponse("New support has been added successfully!", HttpStatus.OK, status);
    
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/supports/solve")
    public ResponseEntity<Object> solveSupport(@RequestPart(name="files", required = false) MultipartFile[] files, @RequestPart("supportId") UUID supportId, @RequestPart("support") SupportRequest supportRequest){
        try {
            Supports support = supportService.solveSupport(supportId, supportRequest);

            if(files != null){
                Arrays.asList(files).stream()
                    .map(file -> supportAttachmentService.addFile(file, support))
                    .collect(Collectors.toList());
            }

            String status = updateTicketStatus(support.getTicket().getTicketId(), EStatus.RESOLVED);
            return ResponseHandler.generateResponse("Support has been updated successfully!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    //withdraw Support
    // public Supports withdrawSupport(UUID supportId, String result, String description){
    //     return supportService.withdrawSupport(supportId, result, description);
    // }

    public String updateTicketStatus(UUID ticketId, EStatus status){
        Tickets ticket = ticketService.updateTicketStatus(ticketId, status);

        return "Ticket "+ticket.getStatus().getName().toString();
    }
    
}
