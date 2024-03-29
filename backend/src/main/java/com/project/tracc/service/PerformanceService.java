package com.project.tracc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracc.data.EStatus;
import com.project.tracc.exception.AbstractGraphQLException;
import com.project.tracc.model.Accounts;
import com.project.tracc.model.Scorings;
import com.project.tracc.model.Status;
import com.project.tracc.model.Supports;
import com.project.tracc.payload.response.PerformanceResponse;
import com.project.tracc.repository.AccountRepository;
import com.project.tracc.repository.StatusRepository;
import com.project.tracc.repository.SupportRepository;

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
    @Autowired
    private final ScoringService scoringService;

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

    @Transactional
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
        
        Status inProgress = statusRepository.findByName(EStatus.IN_PROGRESS).get();
        Status awaiting = statusRepository.findByName(EStatus.AWAITING).get();
        Status resolved = statusRepository.findByName(EStatus.RESOLVED).get();
        Status closed = statusRepository.findByName(EStatus.CLOSED).get();
        Status dropped = statusRepository.findByName(EStatus.DROPPED).get();
        
        int totalInProgress = supportRepository.countSupportByTicketStatus(developer, true, inProgress, startDate, endDate) + supportRepository.countSupportByTicketStatus(developer, true, awaiting, startDate, endDate);;
        int totalResolved = supportRepository.countSupportByTicketStatus(developer, true, resolved, startDate, endDate) + supportRepository.countSupportByTicketStatus(developer, true, closed, startDate, endDate);
        int totalDropped = supportRepository.countSupportByTicketStatus(developer, true, dropped, startDate, endDate);
        int totalReassigned = supportRepository.countByDeveloperAndIsActiveAndDateTakenBetween(developer, false, startDate, endDate);
        int totalTickets = supportRepository.countByDeveloperAndDateTakenBetween(developer,startDate, endDate);

        Scorings scoring = scoringService.getScoring();

        double scoreByStatus = calculateScoreByStatus(scoring, totalResolved, totalDropped, totalReassigned);
        double scoreBySLA = calculateScoreBySLA(scoring, developer, resolved, closed, dropped, startDate, endDate);

        double percentage = (scoreByStatus + scoreBySLA)/2;

        BigDecimal bd = new BigDecimal(Double.toString(percentage));
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        String rate = NumberFormat.getPercentInstance().format(bd);

        return new PerformanceResponse(menu, period, totalInProgress, totalResolved, totalDropped, totalReassigned, totalTickets, rate, label, data);
    }

    @Transactional
    public double calculateScoreByStatus(Scorings scoring, int totalResolved, int totalDropped, int totalReassigned){
        double totalResolvedPoints = totalResolved * scoring.getTicketPoint();
        double totalDroppedPoints = totalDropped * scoring.getTicketPoint();
        double totalReassignedPoints = (totalReassigned * scoring.getTicketPoint()) - (totalReassigned * scoring.getTicketReassignedDeduction());

        double totalPoints = totalResolvedPoints + totalDroppedPoints + totalReassignedPoints;
        double totalTicketPoints = (totalResolved + totalDropped + totalReassigned) * scoring.getTicketPoint();

        double totalScore = 0;
        
        if(totalPoints != 0){
            totalScore = totalPoints / totalTicketPoints;
        }

        return totalScore;
    }

    @Transactional
    public double calculateScoreBySLA(Scorings scoring, Accounts developer, Status resolved, Status closed, Status dropped, OffsetDateTime startDate, OffsetDateTime endDate){
        
        double totalPoints = 0;

        List<Supports> supports = new ArrayList<>();
        List<Supports> supportsResolved = supportRepository.findAllSupportByTicketStatus(developer, true, resolved, startDate, endDate);
        List<Supports> supportsClosed = supportRepository.findAllSupportByTicketStatus(developer, true, closed, startDate, endDate);
        List<Supports> supportsDropped = supportRepository.findAllSupportByTicketStatus(developer, true, dropped, startDate, endDate);

        Stream.of(supportsResolved, supportsClosed, supportsDropped).forEach(supports::addAll);
        
        if(!supports.isEmpty()){
            for(Supports support:supports){
                double ticketPoint = calculatePointsForSupport(support, scoring, support.getDateTaken(), support.getTicket().getDateResolved());
    
                totalPoints += ticketPoint;
            }
        }

        List<Supports> supportsReassigned = supportRepository.findAllByDeveloperAndIsActiveAndDateTakenBetween(developer, false, startDate, endDate);

        if(!supportsReassigned.isEmpty()){
            for(Supports support:supportsReassigned){
                double ticketPoint = calculatePointsForSupport(support, scoring, support.getDateTaken(), support.getDateReassigned());
    
                totalPoints += (ticketPoint - scoring.getTicketReassignedDeduction());
            }
        }

        double totalTicketPoints = (supports.size() + supportsReassigned.size()) * scoring.getTicketPoint();

        double totalScore = 0;

        if(totalPoints != 0){
            totalScore = totalPoints / totalTicketPoints;
        }
        
        return totalScore;
    }

    @Transactional
    public double calculatePointsForSupport(Supports support, Scorings scoring, OffsetDateTime dateTaken, OffsetDateTime dateClosed){
        int days = Period.between(dateTaken.toLocalDate(), dateClosed.toLocalDate()).getDays();
    
        int ticketPoint = scoring.getTicketPoint();
        int ticketSLA = scoring.getTicketSLA();
        int maxTicketSLA = scoring.getMaxTicketSLA();

        if(days > ticketSLA){
            if(days - ticketSLA > maxTicketSLA){
                ticketPoint -= maxTicketSLA;
            } else{
                ticketPoint -= (days - ticketSLA);
            }
        }

        return ticketPoint;
    }
}
