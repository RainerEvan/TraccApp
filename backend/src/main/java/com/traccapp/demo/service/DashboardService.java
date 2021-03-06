package com.traccapp.demo.service;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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

        DashboardActivityResponse thisWeek = calculateActivity("This Week");
        dashboardActivity.add(thisWeek);

        DashboardActivityResponse thisMonth = calculateActivity("This Month");
        dashboardActivity.add(thisMonth);

        DashboardActivityResponse thisYear = calculateActivity("This Year");
        dashboardActivity.add(thisYear);

        return dashboardActivity;
    }

    public DashboardActivityResponse calculateActivity(String menu){
        List<String> label = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        OffsetDateTime startDate = OffsetDateTime.now();
        OffsetDateTime endDate = OffsetDateTime.now();
        String timeframe = "";

        if(menu == "This Week"){
            startDate = OffsetDateTime.now().withHour(0).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            endDate = OffsetDateTime.now().withHour(23).withMinute(59).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

            for(OffsetDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
                String day = date.format(DateTimeFormatter.ofPattern("EE"));
                label.add(day);
    
                int total = ticketRepository.countByDateAddedBetween(date,date.withHour(23).withMinute(59));
                data.add(total);
            }
        }
        else if(menu == "This Month"){
            startDate = OffsetDateTime.now().withHour(0).with(TemporalAdjusters.firstDayOfMonth());
            endDate = OffsetDateTime.now().withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfMonth());
            timeframe = startDate.getMonth().toString();

            for(OffsetDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(1)){
                String day = date.format(DateTimeFormatter.ofPattern("dd"));
                label.add(day);
    
                int total = ticketRepository.countByDateAddedBetween(date,date.withHour(23).withMinute(59));
                data.add(total);
            }
        }
        else if(menu == "This Year"){
            startDate = OffsetDateTime.now().withHour(0).with(TemporalAdjusters.firstDayOfYear());
            endDate = OffsetDateTime.now().withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfYear());
            timeframe = String.valueOf(startDate.getYear());

            for(OffsetDateTime date = startDate; date.isBefore(endDate); date = date.plusMonths(1)){
                String month = date.format(DateTimeFormatter.ofPattern("MMM"));
                label.add(month);
    
                int total = ticketRepository.countByDateAddedBetween(date,date.withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfMonth()));
                data.add(total);
            }
        }

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

        return new DashboardActivityResponse(menu, timeframe, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    }

    // public DashboardActivityResponse calculateActivityThisWeek(){
    //     OffsetDateTime now = OffsetDateTime.now();

    //     OffsetDateTime startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    //     OffsetDateTime endDate = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

    //     String timeframe = "";

    //     Status pending = statusRepository.findByName(EStatus.PENDING).get();
    //     Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
    //     Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
    //     Status closed = statusRepository.findByName(EStatus.CLOSED).get();
    //     Status dropped = statusRepository.findByName(EStatus.DROPPED).get();

    //     int totalPending = ticketRepository.countByStatusAndDateAddedBetween(pending, startDate, endDate);
    //     int totalInProgress = ticketRepository.countByStatusAndDateAddedBetween(inProgress, startDate, endDate);
    //     int totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
    //     int totalDropped = ticketRepository.countByStatusAndDateAddedBetween(dropped, startDate, endDate);
    //     int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

    //     List<String> label = new ArrayList<>();
    //     List<Integer> data = new ArrayList<>();

    //     for(OffsetDateTime date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
    //         String day = date.format(DateTimeFormatter.ofPattern("EE"));
    //         label.add(day);

    //         int total = ticketRepository.countByDateAddedBetween(date,date.plusDays(1));
    //         data.add(total);
    //     }

    //     return new DashboardActivityResponse("This Week", timeframe, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    // }

    // public DashboardActivityResponse calculateActivityThisMonth(){
    //     OffsetDateTime now = OffsetDateTime.now();

    //     OffsetDateTime startDate = now.with(TemporalAdjusters.firstDayOfMonth());
    //     OffsetDateTime endDate = now.with(TemporalAdjusters.lastDayOfMonth());

    //     String timeframe = startDate.getMonth().toString();

    //     Status pending = statusRepository.findByName(EStatus.PENDING).get();
    //     Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
    //     Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
    //     Status closed = statusRepository.findByName(EStatus.CLOSED).get();
    //     Status dropped = statusRepository.findByName(EStatus.DROPPED).get();

    //     int totalPending = ticketRepository.countByStatusAndDateAddedBetween(pending, startDate, endDate);
    //     int totalInProgress = ticketRepository.countByStatusAndDateAddedBetween(inProgress, startDate, endDate);
    //     int totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
    //     int totalDropped = ticketRepository.countByStatusAndDateAddedBetween(dropped, startDate, endDate);
    //     int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

    //     List<String> label = new ArrayList<>();
    //     List<Integer> data = new ArrayList<>();

    //     for(OffsetDateTime date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
    //         String day = date.format(DateTimeFormatter.ofPattern("dd"));
    //         label.add(day);

    //         int total = ticketRepository.countByDateAddedBetween(date,date.plusDays(1));
    //         data.add(total);
    //     }

    //     return new DashboardActivityResponse("This Month", timeframe, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    // }

    // public DashboardActivityResponse calculateActivityThisYear(){
    //     OffsetDateTime now = OffsetDateTime.now();

    //     OffsetDateTime startDate = now.with(TemporalAdjusters.firstDayOfYear());
    //     OffsetDateTime endDate = now.with(TemporalAdjusters.lastDayOfYear());

    //     String timeframe = String.valueOf(startDate.getYear());

    //     Status pending = statusRepository.findByName(EStatus.PENDING).get();
    //     Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
    //     Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
    //     Status closed = statusRepository.findByName(EStatus.CLOSED).get();
    //     Status dropped = statusRepository.findByName(EStatus.DROPPED).get();

    //     int totalPending = ticketRepository.countByStatusAndDateAddedBetween(pending, startDate, endDate);
    //     int totalInProgress = ticketRepository.countByStatusAndDateAddedBetween(inProgress, startDate, endDate);
    //     int totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
    //     int totalDropped = ticketRepository.countByStatusAndDateAddedBetween(dropped, startDate, endDate);
    //     int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

    //     List<String> label = new ArrayList<>();
    //     List<Integer> data = new ArrayList<>();

    //     for(OffsetDateTime date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusMonths(1)){
    //         String month = date.format(DateTimeFormatter.ofPattern("MMM"));
    //         label.add(month);

    //         int total = ticketRepository.countByDateAddedBetween(date,date.with(TemporalAdjusters.lastDayOfMonth()));
    //         data.add(total);
    //     }

    //     return new DashboardActivityResponse("This Year", timeframe, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    // }
}
