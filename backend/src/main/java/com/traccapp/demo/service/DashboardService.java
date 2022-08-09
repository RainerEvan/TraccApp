package com.traccapp.demo.service;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.payload.response.DashboardActivityResponse;
import com.traccapp.demo.payload.response.DashboardAnalyticsResponse;
import com.traccapp.demo.payload.response.TicketRateResponse;
import com.traccapp.demo.payload.response.TopApplicationsResponse;
import com.traccapp.demo.payload.response.TopTagsResponse;
import com.traccapp.demo.repository.StatusRepository;
import com.traccapp.demo.repository.SupportRepository;
import com.traccapp.demo.repository.TicketRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardService {
    
    @Autowired
    private final TicketRepository ticketRepository;
    @Autowired
    private final SupportRepository supportRepository;
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

    public List<DashboardAnalyticsResponse> getDashboardAnalytics(){
        List<DashboardAnalyticsResponse> dashboardAnalytics = new ArrayList<>();
        boolean isExist = false;
        OffsetDateTime now = OffsetDateTime.now();

        do{
            OffsetDateTime startDate = now.withHour(0).with(TemporalAdjusters.firstDayOfYear());
            OffsetDateTime endDate = now.withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfYear());

            isExist = ticketRepository.existsByDateAddedBetween(startDate, endDate);

            if(isExist){
                DashboardAnalyticsResponse analytics = calculateAnalytics(now);
                dashboardAnalytics.add(analytics);
            }

            now = now.minusYears(1);

        } while(isExist);

        return dashboardAnalytics;
    }

    public DashboardActivityResponse calculateActivity(String menu){
        List<String> label = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        OffsetDateTime startDate = OffsetDateTime.now();
        OffsetDateTime endDate = OffsetDateTime.now();
        String period = "";

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
            period = startDate.getMonth().toString();

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
            period = String.valueOf(startDate.getYear());

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

        return new DashboardActivityResponse(menu, period, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    }

    public DashboardAnalyticsResponse calculateAnalytics(OffsetDateTime now){
        List<String> label = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        OffsetDateTime startDate = now.withHour(0).with(TemporalAdjusters.firstDayOfYear());
        OffsetDateTime endDate = now.withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfYear());

        for(OffsetDateTime date = startDate; date.isBefore(endDate); date = date.plusMonths(1)){
            String month = date.format(DateTimeFormatter.ofPattern("MMM"));
            label.add(month);

            int total = ticketRepository.countByDateAddedBetween(date,date.withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfMonth()));
            data.add(total);
        }

        // List<Integer> sample = data;
        // sample.removeIf(n -> Objects.equals(n, 0));

        String period = String.valueOf(now.getYear());
        int minTickets = Collections.min(data);
        int maxTickets = Collections.max(data);
        int avgTickets = (data.stream().reduce(0, Integer::sum)/data.size());
        int totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

        List<TopApplicationsResponse> topApplications = calculateTopApplications(startDate, endDate);
        List<TopTagsResponse> topTags = calculateTopTags(startDate, endDate);
        TicketRateResponse ticketRate = calculateTicketRate(startDate, endDate);

        return new DashboardAnalyticsResponse(period, minTickets, maxTickets, avgTickets, totalTickets, label, data, topApplications, topTags, ticketRate);
    }

    public List<TopApplicationsResponse> calculateTopApplications(OffsetDateTime startDate, OffsetDateTime endDate){
        List<TopApplicationsResponse> topApplicationsList = ticketRepository.countTicketByApplication(startDate, endDate);
        
        Collections.sort(topApplicationsList, (o1,o2) -> (int) o1.getCount() - (int) o2.getCount());
        Collections.reverse(topApplicationsList);

        if(topApplicationsList.size() > 3){
            List<TopApplicationsResponse> top3Applications = new ArrayList<>();

            for(int i=0;i<3;i++){
                top3Applications.add(topApplicationsList.get(0));
                topApplicationsList.remove(0);
            }
    
            long count = topApplicationsList.stream().reduce(0, (total,app) -> total + (int) app.getCount(), Integer::sum);
            TopApplicationsResponse otherApplications = new TopApplicationsResponse("OTHER", count);
            top3Applications.add(otherApplications);
    
            return top3Applications;
        }

        return topApplicationsList;
    }

    public List<TopTagsResponse> calculateTopTags(OffsetDateTime startDate, OffsetDateTime endDate){
        List<TopTagsResponse> topTagsList = supportRepository.countSupportByTag(startDate, endDate);
        
        Collections.sort(topTagsList, (o1,o2) -> (int) o1.getCount() - (int) o2.getCount());
        Collections.reverse(topTagsList);

        return topTagsList.stream().limit(3).toList();
    }

    public TicketRateResponse calculateTicketRate(OffsetDateTime startDate, OffsetDateTime endDate){
        Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
        Status closed = statusRepository.findByName(EStatus.CLOSED).get();

        double totalResolved = ticketRepository.countByStatusAndDateAddedBetween(resolved, startDate, endDate) + ticketRepository.countByStatusAndDateAddedBetween(closed, startDate, endDate);
        double totalTickets = ticketRepository.countByDateAddedBetween(startDate, endDate);

        double percentage = totalResolved/totalTickets;
        String rate = NumberFormat.getPercentInstance().format(percentage);
        String label = "";

        if(percentage == 1){
            label = "PERFECT";
        }
        else if(percentage >= 0.9){
            label = "GREAT";
        }
        else if(percentage >= 0.8){
            label = "GOOD";
        }
        else if(percentage >= 0.7){
            label = "ACCEPTABLE";
        }
        else if(percentage >= 0.6){
            label = "BAD";
        }
        else{
            label = "POOR";
        }

        return new TicketRateResponse(label,rate);
    }

    // public DashboardActivityResponse calculateActivityThisWeek(){
    //     OffsetDateTime now = OffsetDateTime.now();

    //     OffsetDateTime startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    //     OffsetDateTime endDate = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

    //     String period = "";

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

    //     return new DashboardActivityResponse("This Week", period, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    // }

    // public DashboardActivityResponse calculateActivityThisMonth(){
    //     OffsetDateTime now = OffsetDateTime.now();

    //     OffsetDateTime startDate = now.with(TemporalAdjusters.firstDayOfMonth());
    //     OffsetDateTime endDate = now.with(TemporalAdjusters.lastDayOfMonth());

    //     String period = startDate.getMonth().toString();

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

    //     return new DashboardActivityResponse("This Month", period, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    // }

    // public DashboardActivityResponse calculateActivityThisYear(){
    //     OffsetDateTime now = OffsetDateTime.now();

    //     OffsetDateTime startDate = now.with(TemporalAdjusters.firstDayOfYear());
    //     OffsetDateTime endDate = now.with(TemporalAdjusters.lastDayOfYear());

    //     String period = String.valueOf(startDate.getYear());

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

    //     return new DashboardActivityResponse("This Year", period, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, label, data);
    // }
}
