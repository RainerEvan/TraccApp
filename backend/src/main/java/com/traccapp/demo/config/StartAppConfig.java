package com.traccapp.demo.config;

import java.time.OffsetDateTime;

import com.traccapp.demo.data.ERoles;
import com.traccapp.demo.data.EStatus;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Applications;
import com.traccapp.demo.model.Comments;
import com.traccapp.demo.model.Divisions;
import com.traccapp.demo.model.Roles;
import com.traccapp.demo.model.Status;
import com.traccapp.demo.model.Supports;
import com.traccapp.demo.model.Tags;
import com.traccapp.demo.model.Tickets;
import com.traccapp.demo.payload.request.AccountRequest;
import com.traccapp.demo.repository.CommentRepository;
import com.traccapp.demo.repository.RoleRepository;
import com.traccapp.demo.repository.StatusRepository;
import com.traccapp.demo.repository.SupportRepository;
import com.traccapp.demo.repository.TicketRepository;
import com.traccapp.demo.service.AccountService;
import com.traccapp.demo.service.ApplicationService;
import com.traccapp.demo.service.DivisionService;
import com.traccapp.demo.service.TagsService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartAppConfig {
    @Bean
    CommandLineRunner commandLineRunner(TagsService tagsService, AccountService accountService, ApplicationService applicationService, DivisionService divisionService, RoleRepository roleRepository, StatusRepository statusRepository, TicketRepository ticketRepository, SupportRepository supportRepository, CommentRepository commentRepository){
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

            Tags tag = tagsService.addTag("Error");
            Tags tag2 = tagsService.addTag("Bug");

            Applications application = applicationService.addApplication("CAMS");
            Applications application2 = applicationService.addApplication("SMILE");
            Applications application3 = applicationService.addApplication("GEOSPACIAL");
            Divisions division = divisionService.addDivision("ITX A");
            Divisions division2 = divisionService.addDivision("ITX B");
            Divisions division3 = divisionService.addDivision("ITX C");

            AccountRequest accountRequest = new AccountRequest();
            accountRequest.setUsername("maman");
            accountRequest.setFullname("Maman Sulaiman");
            accountRequest.setPassword("pass123");
            accountRequest.setEmail("maman@gmail.com");
            accountRequest.setContactNo("092039031");
            accountRequest.setDivisionId(division.getId());
            accountRequest.setIsActive(true);
            accountRequest.setRolesName(ERoles.USER);
            
            Accounts account = accountService.addAccount(null,accountRequest);

            AccountRequest accountRequest2 = new AccountRequest();
            accountRequest2.setUsername("rainer");
            accountRequest2.setFullname("Rainer Evan");
            accountRequest2.setPassword("pass123");
            accountRequest2.setEmail("rainer@gmail.com");
            accountRequest2.setContactNo("092039031");
            accountRequest2.setDivisionId(division.getId());
            accountRequest2.setIsActive(true);
            accountRequest2.setRolesName(ERoles.DEVELOPER);
            
            Accounts account2 = accountService.addAccount(null,accountRequest2);

            AccountRequest accountRequest3 = new AccountRequest();
            accountRequest3.setUsername("admin");
            accountRequest3.setFullname("Admin 1");
            accountRequest3.setPassword("pass123");
            accountRequest3.setEmail("admin@gmail.com");
            accountRequest3.setContactNo("092039031");
            accountRequest3.setDivisionId(division.getId());
            accountRequest3.setIsActive(true);
            accountRequest3.setRolesName(ERoles.ADMIN);
            
            Accounts account3 = accountService.addAccount(null,accountRequest3);

            AccountRequest accountRequest4 = new AccountRequest();
            accountRequest4.setUsername("bob");
            accountRequest4.setFullname("Bob");
            accountRequest4.setPassword("pass123");
            accountRequest4.setEmail("bob@gmail.com");
            accountRequest4.setContactNo("092002031");
            accountRequest4.setDivisionId(division.getId());
            accountRequest4.setIsActive(true);
            accountRequest4.setRolesName(ERoles.DEVELOPER);
            
            Accounts account4 = accountService.addAccount(null,accountRequest4);
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
            ticket.setDateAdded(OffsetDateTime.now());
            ticket.setApplication(application);
            ticket.setReporter(account);
            ticket.setTitle("Aplikasi CAMS error");
            ticket.setDescription("Form gabisa diisi lagi gimana dong");
            ticket.setStatus(status1);
            ticketRepository.save(ticket);

            Tickets ticket2 = new Tickets();
            ticket2.setDateAdded(OffsetDateTime.now());
            ticket2.setApplication(application2);
            ticket2.setReporter(account);
            ticket2.setTitle("Smile Bug pada Form Tidak ada alamat");
            ticket2.setDescription("Form gabisa diisi lagi gimana dong");
            ticket2.setStatus(status2);
            ticketRepository.save(ticket2);

            Tickets ticket3 = new Tickets();
            ticket3.setDateAdded(OffsetDateTime.now());
            ticket3.setApplication(application3);
            ticket3.setReporter(account);
            ticket3.setTitle("App Geospacial Tidak bisa Input Data Perumahan");
            ticket3.setDescription("Form gabisa diisi lagi gimana dong");
            ticket3.setStatus(status3);
            ticketRepository.save(ticket3);

            Supports support = new Supports();
            support.setTicket(ticket2);
            support.setDateTaken(OffsetDateTime.now());
            support.setDeveloper(account2);
            support.setIsActive(true);
            supportRepository.save(support);

            Supports support2 = new Supports();
            support2.setTicket(ticket3);
            support2.setDateTaken(OffsetDateTime.now());
            support2.setDeveloper(account2);
            support2.setResult("Form sudah dibenerin");
            support2.setDescription("Formnya udah bisa dipake coba lagi bisa ga");
            support2.setIsActive(true);
            supportRepository.save(support2);

            Comments comment = new Comments();
            comment.setTicket(ticket3);
            comment.setAuthor(account);
            comment.setBody("Tolong ini gimana caranya saya bener-bener bingung gatau harus gimana lagi");
            comment.setCreatedAt(OffsetDateTime.now());
            comment.setIsActive(true);
            commentRepository.save(comment);

            // Supports support2 = new Supports();
            // support2.setTicket(ticket);
            // support2.setDateTaken(LocalDate.now());
            // support2.setDeveloper(account);
            // support2.setIsActive(false);
            // supportRepository.save(support2);
        };
    }
}
