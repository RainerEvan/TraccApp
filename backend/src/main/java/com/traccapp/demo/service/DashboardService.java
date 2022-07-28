package com.traccapp.demo.service;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.payload.response.DashboardActivityResponse;
import com.traccapp.demo.repository.StatusRepository;
import com.traccapp.demo.repository.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardService {
    
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final StatusRepository statusRepository;

    public List<DashboardActivityResponse> getDashboardActivity(){
        List<DashboardActivityResponse> dashboardActivity = new ArrayList<>();

        DashboardActivityResponse thisWeek = calculateActivityThisWeek();
        dashboardActivity.add(thisWeek);

        DashboardActivityResponse thisMonth = calculateActivityThisMonth();
        dashboardActivity.add(thisMonth);

        DashboardActivityResponse thisYear = calculateActivityThisYear();
        dashboardActivity.add(thisYear);

        return dashboardActivity;
    }

    public DashboardActivityResponse calculateActivityThisWeek(){
        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        OffsetDateTime endDate = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        Status pending = statusRepository.findByName(EStatus.PENDING).get();
        Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
        Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
        Status closed = statusRepository.findByName(EStatus.CLOSED).get();
        Status dropped = statusRepository.findByName(EStatus.DROPPED).get();

        int totalPending = ticketRepository.countByStatusAndDateAddedBetween(pending, startDate, endDate);
        int totalInProgress = ticketRepository.countByStatusAndDateAddedBetween(inProgress, startDate, endDate);
        int totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
        int totalDropped = ticketRepository.countByStatusAndDateAddedBetween(dropped, startDate, endDate);
        int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

        List<String> label = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        return new DashboardActivityResponse("This Week", totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    }

    public DashboardActivityResponse calculateActivityThisMonth(){
        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime startDate = now.with(TemporalAdjusters.firstDayOfMonth());
        OffsetDateTime endDate = now.with(TemporalAdjusters.lastDayOfMonth());

        Status pending = statusRepository.findByName(EStatus.PENDING).get();
        Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
        Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
        Status closed = statusRepository.findByName(EStatus.CLOSED).get();
        Status dropped = statusRepository.findByName(EStatus.DROPPED).get();

        int totalPending = ticketRepository.countByStatusAndDateAddedBetween(pending, startDate, endDate);
        int totalInProgress = ticketRepository.countByStatusAndDateAddedBetween(inProgress, startDate, endDate);
        int totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
        int totalDropped = ticketRepository.countByStatusAndDateAddedBetween(dropped, startDate, endDate);
        int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

        List<String> label = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        return new DashboardActivityResponse("This Month", totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    }

    public DashboardActivityResponse calculateActivityThisYear(){
        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime startDate = now.with(TemporalAdjusters.firstDayOfYear());
        OffsetDateTime endDate = now.with(TemporalAdjusters.lastDayOfYear());

        Status pending = statusRepository.findByName(EStatus.PENDING).get();
        Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
        Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
        Status closed = statusRepository.findByName(EStatus.CLOSED).get();
        Status dropped = statusRepository.findByName(EStatus.DROPPED).get();

        int totalPending = ticketRepository.countByStatusAndDateAddedBetween(pending, startDate, endDate);
        int totalInProgress = ticketRepository.countByStatusAndDateAddedBetween(inProgress, startDate, endDate);
        int totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
        int totalDropped = ticketRepository.countByStatusAndDateAddedBetween(dropped, startDate, endDate);
        int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

        List<String> label = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        return new DashboardActivityResponse("This Year", totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    }
}
