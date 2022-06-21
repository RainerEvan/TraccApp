package com.traccapp.demo.config;

import java.time.LocalDate;
import java.util.Set;

import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.model.Roles;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.request.AccountRequest;
import com.traccapp.demo.repository.RoleRepository;
import com.traccapp.demo.repository.StatusRepository;
import com.traccapp.demo.repository.SupportRepository;
import com.traccapp.demo.repository.TicketRepository;
import com.traccapp.demo.service.AccountService;
import com.traccapp.demo.service.ApplicationService;
import com.traccapp.demo.service.DivisionService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StartAppConfig {
    @Bean
    CommandLineRunner commandLineRunner(AccountService accountService, ApplicationService applicationService, DivisionService divisionService, RoleRepository roleRepository, StatusRepository statusRepository, TicketRepository ticketRepository, SupportRepository supportRepository){
        return args -> {

            Roles role1 = new Roles();
            role1.setName(ERoles.ADMIN);
            roleRepository.save(role1);

            Roles role2 = new Roles();
            role2.setName(ERoles.SUPERVISOR);
            roleRepository.save(role2);

            Roles role3 = new Roles();
            role3.setName(ERoles.DEVELOPER);
            roleRepository.save(role3);

            Roles role4 = new Roles();
            role4.setName(ERoles.USER);
            roleRepository.save(role4);

            Status status1 = new Status();
            status1.setName(EStatus.PENDING);
            statusRepository.save(status1);

            Status status2 = new Status();
            status2.setName(EStatus.IN_PROGRESS);
            statusRepository.save(status2);

            Status status3 = new Status();
            status3.setName(EStatus.RESOLVED);
            statusRepository.save(status3);

            Status status4 = new Status();
            status4.setName(EStatus.CLOSED);
            statusRepository.save(status4);

            Status status5 = new Status();
            status5.setName(EStatus.DROPPED);
            statusRepository.save(status5);

            Applications application = applicationService.addApplication("CAMS");
            Applications application2 = applicationService.addApplication("SMILE");
            Applications application3 = applicationService.addApplication("GEOSPACIAL");
            Applications application4 = applicationService.addApplication("UIDM");
            Divisions division = divisionService.addDivision("ITXB");

            AccountRequest accountRequest = new AccountRequest();
            accountRequest.setUsername("maman");
            accountRequest.setFullname("Maman Sulaiman");
            accountRequest.setPassword("pass123");
            accountRequest.setEmail("maman@gmail.com");
            accountRequest.setContactNo("092039031");
            accountRequest.setDivisionId(division.getId());
            accountRequest.setIsActive(true);
            accountRequest.setRoles(Set.of(ERoles.USER));
            
            Accounts account = accountService.addAccount(null,accountRequest);

            AccountRequest accountRequest2 = new AccountRequest();
            accountRequest2.setUsername("rainer");
            accountRequest2.setFullname("Rainer Evan");
            accountRequest2.setPassword("pass123");
            accountRequest2.setEmail("rainer@gmail.com");
            accountRequest2.setContactNo("092039031");
            accountRequest2.setDivisionId(division.getId());
            accountRequest2.setIsActive(true);
            accountRequest2.setRoles(Set.of(ERoles.DEVELOPER));
            
            Accounts account2 = accountService.addAccount(null,accountRequest2);

            // for(int i=0;i<2;i++){
                
            //     Tickets ticket = new Tickets();
            //     ticket.setDateAdded(LocalDate.now());
            //     ticket.setApplication(application);
            //     ticket.setReporter(account);
            //     ticket.setTitle("Aplikasi CAMS error");
            //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
            //     ticket.setStatus(status1);
            //     ticketRepository.save(ticket);
            // }

            Tickets ticket = new Tickets();
            ticket.setDateAdded(LocalDate.now());
            ticket.setApplication(application);
            ticket.setReporter(account);
            ticket.setTitle("Aplikasi CAMS error");
            ticket.setDescription("Form gabisa diisi lagi gimana dong");
            ticket.setStatus(status1);
            ticketRepository.save(ticket);

            Tickets ticket2 = new Tickets();
            ticket2.setDateAdded(LocalDate.now());
            ticket2.setApplication(application);
            ticket2.setReporter(account);
            ticket2.setTitle("Aplikasi CAMS error");
            ticket2.setDescription("Form gabisa diisi lagi gimana dong");
            ticket2.setStatus(status1);
            ticketRepository.save(ticket2);

            Tickets ticket3 = new Tickets();
            ticket3.setDateAdded(LocalDate.now());
            ticket3.setApplication(application);
            ticket3.setReporter(account);
            ticket3.setTitle("Aplikasi CAMS error");
            ticket3.setDescription("Form gabisa diisi lagi gimana dong");
            ticket3.setStatus(status1);
            ticketRepository.save(ticket3);

            Supports support = new Supports();
            support.setTicket(ticket2);
            support.setDateTaken(LocalDate.now());
            support.setDeveloper(account2);
            support.setIsActive(true);
            supportRepository.save(support);

            Supports support2 = new Supports();
            support2.setTicket(ticket3);
            support2.setDateTaken(LocalDate.now());
            support2.setDeveloper(account2);
            support2.setResult("Form sudah dibenerin");
            support2.setDescription("Formnya udah bisa dipake coba lagi bisa ga");
            support2.setIsActive(true);
            supportRepository.save(support2);

            // Supports support2 = new Supports();
            // support2.setTicket(ticket);
            // support2.setDateTaken(LocalDate.now());
            // support2.setDeveloper(account);
            // support2.setIsActive(false);
            // supportRepository.save(support2);
        };
    }
}
