package com.project.tracc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.tracc.data.EStatus;
import com.project.tracc.model.Supports;
import com.project.tracc.model.TicketAttachments;
import com.project.tracc.model.TicketLogs;
import com.project.tracc.model.Tickets;
import com.project.tracc.payload.request.AssignSupportRequest;
import com.project.tracc.payload.request.ReassignSupportRequest;
import com.project.tracc.payload.request.SupportRequest;
import com.project.tracc.payload.request.TicketRequest;
import com.project.tracc.service.SupportAttachmentService;
import com.project.tracc.service.SupportService;
import com.project.tracc.service.TicketAttachmentService;
import com.project.tracc.service.TicketLogsService;
import com.project.tracc.service.TicketService;
import com.project.tracc.utils.ResponseHandler;

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
    @Autowired
    private final TicketLogsService ticketLogsService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addTicket(@RequestPart(name="files", required = false) MultipartFile[] files, @RequestPart("ticket") TicketRequest ticketRequest){
        try{
            Tickets ticket = ticketService.addTicket(ticketRequest);

            if(files != null){
                Arrays.asList(files).stream()
                    .map(file -> ticketAttachmentService.addFile(file, ticket))
                    .collect(Collectors.toList());
            }

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(ticket.getTicketId(), "New ticket created");
            ticketLogsService.logTicketInfo(ticketLogs);

            return ResponseHandler.generateResponse("Ticket has been added successfully!", HttpStatus.OK, ticket.getTicketNo());

        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/close")
    public ResponseEntity<Object> closeTicket(@RequestParam("ticketId") UUID ticketId){
        try {
            String status = updateTicketStatus(ticketId, EStatus.CLOSED);

            Tickets ticket = ticketService.getTicket(ticketId);

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(ticket.getTicketId(), "Ticket closed");
            ticketLogsService.logTicketInfo(ticketLogs);

            return ResponseHandler.generateResponse("Ticket has been closed!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/drop")
    public ResponseEntity<Object> dropTicket(@RequestParam("ticketId") UUID ticketId){
        try {
            String status = updateTicketStatus(ticketId, EStatus.DROPPED);

            Tickets ticket = ticketService.getTicket(ticketId);

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(ticket.getTicketId(), "Ticket dropped");
            ticketLogsService.logTicketInfo(ticketLogs);

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

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(ticket.getTicketId(), "Ticket canceled");
            ticketLogsService.logTicketInfo(ticketLogs);

            return ResponseHandler.generateResponse("Ticket has been canceled!", HttpStatus.OK, null);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/request-drop")
    public ResponseEntity<Object> requestDropTicket(@RequestParam("supportId") UUID supportId, @RequestBody SupportRequest supportRequest){
        try {
            Supports support = supportService.requestDropSupport(supportId, supportRequest);
            String status = updateTicketStatus(support.getTicket().getTicketId(), EStatus.AWAITING);

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(support.getTicket().getTicketId(), "Request drop ticket");
            ticketLogsService.logTicketInfo(ticketLogs);

            return ResponseHandler.generateResponse("Ticket is now awaiting for approval!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/supports/add")
    public ResponseEntity<Object> addSupport(@RequestBody AssignSupportRequest assignSupportRequest){
        try {
            Supports support = supportService.addSupport(assignSupportRequest);
            String status = updateTicketStatus(support.getTicket().getTicketId(), EStatus.IN_PROGRESS);

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(support.getTicket().getTicketId(), "Ticket taken for support");
            ticketLogsService.logTicketInfo(ticketLogs);

            return ResponseHandler.generateResponse("Support has been added successfully!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/supports/reassign")
    public ResponseEntity<Object> reassignSupport(@RequestBody ReassignSupportRequest reassignSupportRequest){
        try {
            supportService.deactivateSupport(reassignSupportRequest.getCurrSupportId());
            Supports support = supportService.reassignSupport(reassignSupportRequest);
            String status = updateTicketStatus(support.getTicket().getTicketId(), EStatus.IN_PROGRESS);

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(support.getTicket().getTicketId(), "Ticket reassigned to new support");
            ticketLogsService.logTicketInfo(ticketLogs);

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

            TicketLogs ticketLogs = ticketLogsService.addTicketLogs(support.getTicket().getTicketId(), "Ticket solved");
            ticketLogsService.logTicketInfo(ticketLogs);

            return ResponseHandler.generateResponse("Support has been updated successfully!", HttpStatus.OK, status);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    public String updateTicketStatus(UUID ticketId, EStatus status){
        Tickets ticket = ticketService.updateTicketStatus(ticketId, status);

        return "Ticket "+ticket.getStatus().getName().toString();
    }
    
}
