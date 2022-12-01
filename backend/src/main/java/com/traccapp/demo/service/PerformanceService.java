package com.traccapp.demo.service;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.payload.response.PerformanceResponse;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.StatusRepository;
import com.traccapp.demo.repository.SupportRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerformanceService {
    
    @Autowired
    private final SupportRepository supportRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final StatusRepository statusRepository;

    public List<PerformanceResponse> getPerformanceForDeveloper(UUID accountId){
        Accounts developer = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        List<PerformanceResponse> dashboardActivity = new ArrayList<>();

        PerformanceResponse thisWeek = calculatePerformance(developer,"This Week");
        dashboardActivity.add(thisWeek);

        PerformanceResponse thisMonth = calculatePerformance(developer,"This Month");
        dashboardActivity.add(thisMonth);

        PerformanceResponse thisYear = calculatePerformance(developer,"This Year");
        dashboardActivity.add(thisYear);

        return dashboardActivity;
    }

    public PerformanceResponse calculatePerformance(Accounts developer, String menu){
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
    
                int total = supportRepository.countByDeveloperAndDateTakenBetween(developer,date,date.withHour(23).withMinute(59));
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
    
                int total = supportRepository.countByDeveloperAndDateTakenBetween(developer,date,date.withHour(23).withMinute(59));
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
    
                int total = supportRepository.countByDeveloperAndDateTakenBetween(developer,date,date.withHour(23).withMinute(59).with(TemporalAdjusters.lastDayOfMonth()));
                data.add(total);
            }
        }

        
        Status pending = statusRepository.findByName(EStatus.PENDING).get();
        Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
        Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
        Status closed = statusRepository.findByName(EStatus.CLOSED).get();
        Status dropped = statusRepository.findByName(EStatus.DROPPED).get();
        
        int totalPending = supportRepository.countSupportByTicketStatus(developer, true, pending, startDate, endDate);
        int totalInProgress = supportRepository.countSupportByTicketStatus(developer, true, inProgress, startDate, endDate);
        int totalResolved = supportRepository.countSupportByTicketStatus(developer, true, resolved, startDate, endDate) + supportRepository.countSupportByTicketStatus(developer, true, closed, startDate, endDate);
        int totalDropped = supportRepository.countSupportByTicketStatus(developer, true, dropped, startDate, endDate);
        int totalTickets = supportRepository.countByDeveloperAndDateTakenBetween(developer,startDate, endDate);

        double percentage = (double) totalResolved/ (double) totalTickets;
        String rate = NumberFormat.getPercentInstance().format(percentage);

        return new PerformanceResponse(menu, period, totalPending, totalInProgress, totalResolved, totalDropped, totalTickets, rate, label, data);
    }

}
