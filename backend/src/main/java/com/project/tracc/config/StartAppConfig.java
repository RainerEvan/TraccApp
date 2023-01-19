// package com.project.tracc.config;

// import java.time.OffsetDateTime;
// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.project.tracc.data.ERoles;
// import com.project.tracc.data.EStatus;
// import com.project.tracc.model.Accounts;
// import com.project.tracc.model.Applications;
// import com.project.tracc.model.Comments;
// import com.project.tracc.model.Divisions;
// import com.project.tracc.model.Members;
// import com.project.tracc.model.Notifications;
// import com.project.tracc.model.Roles;
// import com.project.tracc.model.Scorings;
// import com.project.tracc.model.Status;
// import com.project.tracc.model.Supports;
// import com.project.tracc.model.Tags;
// import com.project.tracc.model.Teams;
// import com.project.tracc.model.Tickets;
// import com.project.tracc.payload.request.AccountRequest;
// import com.project.tracc.payload.request.ScoringRequest;
// import com.project.tracc.repository.CommentRepository;
// import com.project.tracc.repository.MemberRepository;
// import com.project.tracc.repository.NotificationRepository;
// import com.project.tracc.repository.RoleRepository;
// import com.project.tracc.repository.StatusRepository;
// import com.project.tracc.repository.SupportRepository;
// import com.project.tracc.repository.TeamRepository;
// import com.project.tracc.repository.TicketRepository;
// import com.project.tracc.service.AccountService;
// import com.project.tracc.service.ApplicationService;
// import com.project.tracc.service.DivisionService;
// import com.project.tracc.service.ScoringService;
// import com.project.tracc.service.TagsService;

// @Configuration
// public class StartAppConfig {
//     @Bean
//     CommandLineRunner commandLineRunner(TagsService tagsService, AccountService accountService, ApplicationService applicationService, DivisionService divisionService, ScoringService scoreConfigService, RoleRepository roleRepository, StatusRepository statusRepository, TicketRepository ticketRepository, SupportRepository supportRepository, CommentRepository commentRepository, NotificationRepository notificationRepository, TeamRepository teamRepository, MemberRepository memberRepository){
//         return args -> {

//             Roles role1 = new Roles();
//             role1.setName(ERoles.ADMIN);
//             roleRepository.save(role1);

//             Roles role2 = new Roles();
//             role2.setName(ERoles.SUPERVISOR);
//             roleRepository.save(role2);

//             Roles role3 = new Roles();
//             role3.setName(ERoles.DEVELOPER);
//             roleRepository.save(role3);

//             Roles role4 = new Roles();
//             role4.setName(ERoles.USER);
//             roleRepository.save(role4);

//             Status status1 = new Status();
//             status1.setName(EStatus.PENDING);
//             statusRepository.save(status1);

//             Status status2 = new Status();
//             status2.setName(EStatus.IN_PROGRESS);
//             statusRepository.save(status2);

//             Status status3 = new Status();
//             status3.setName(EStatus.RESOLVED);
//             statusRepository.save(status3);

//             Status status4 = new Status();
//             status4.setName(EStatus.CLOSED);
//             statusRepository.save(status4);

//             Status status5 = new Status();
//             status5.setName(EStatus.DROPPED);
//             statusRepository.save(status5);

//             Status status6 = new Status();
//             status6.setName(EStatus.AWAITING);
//             statusRepository.save(status6);

//             Tags tag = tagsService.addTag("Error");
//             Tags tag2 = tagsService.addTag("Bug");
//             Tags tag3 = tagsService.addTag("Data Output");
//             Tags tag4 = tagsService.addTag("Network Error");
            
//             // Set<Tags> tags = new HashSet<>();
//             // tags.add(tag);
//             // tags.add(tag2);

//             // Set<Tags> tags2 = new HashSet<>();
//             // tags2.add(tag);
//             // tags2.add(tag3);
//             // tags2.add(tag4);

//             ScoringRequest scoringRequest = new ScoringRequest(10, 2, 5, 2);
//             Scorings scoreConfig = scoreConfigService.addScoring(scoringRequest);

//             Applications application = applicationService.addApplication("CAMS");
//             Applications application2 = applicationService.addApplication("SMILE");
//             Applications application3 = applicationService.addApplication("GEOSPACIAL");
//             Applications application4 = applicationService.addApplication("UIDM");
//             Applications application5 = applicationService.addApplication("CCOS");
//             Divisions division = divisionService.addDivision("ITX A");
//             Divisions division2 = divisionService.addDivision("ITX B");
//             Divisions division3 = divisionService.addDivision("ITX C");

//             // AccountRequest accountRequest = new AccountRequest();
//             // accountRequest.setUsername("maman");
//             // accountRequest.setFullname("Maman Sulaiman");
//             // accountRequest.setPassword("pass123");
//             // accountRequest.setEmail("maman@gmail.com");
//             // accountRequest.setContactNo("092039031");
//             // accountRequest.setDivisionId(division2.getId());
//             // accountRequest.setIsActive(true);
//             // accountRequest.setRoleId(role4.getId());
            
//             // Accounts account = accountService.addAccount(null,accountRequest);

//             // AccountRequest accountRequest2 = new AccountRequest();
//             // accountRequest2.setUsername("rainer");
//             // accountRequest2.setFullname("Rainer Evan");
//             // accountRequest2.setPassword("pass123");
//             // accountRequest2.setEmail("rainer.evan@binus.ac.id");
//             // accountRequest2.setContactNo("092039031");
//             // accountRequest2.setDivisionId(division2.getId());
//             // accountRequest2.setIsActive(true);
//             // accountRequest2.setRoleId(role3.getId());
            
//             // Accounts account2 = accountService.addAccount(null,accountRequest2);

//             // AccountRequest accountRequest3 = new AccountRequest();
//             // accountRequest3.setUsername("admin");
//             // accountRequest3.setFullname("Admin 1");
//             // accountRequest3.setPassword("pass123");
//             // accountRequest3.setEmail("admin@gmail.com");
//             // accountRequest3.setContactNo("092039031");
//             // accountRequest3.setDivisionId(division2.getId());
//             // accountRequest3.setIsActive(true);
//             // accountRequest3.setRoleId(role1.getId());
            
//             // Accounts account3 = accountService.addAccount(null,accountRequest3);

//             // AccountRequest accountRequest4 = new AccountRequest();
//             // accountRequest4.setUsername("bob");
//             // accountRequest4.setFullname("Bob");
//             // accountRequest4.setPassword("pass123");
//             // accountRequest4.setEmail("bob@gmail.com");
//             // accountRequest4.setContactNo("092002031");
//             // accountRequest4.setDivisionId(division2.getId());
//             // accountRequest4.setIsActive(true);
//             // accountRequest4.setRoleId(role2.getId());
            
//             // Accounts account4 = accountService.addAccount(null,accountRequest4);

//             // AccountRequest accountRequest5 = new AccountRequest();
//             // accountRequest5.setUsername("evantanu");
//             // accountRequest5.setFullname("Evan Tanu");
//             // accountRequest5.setPassword("pass123");
//             // accountRequest5.setEmail("evan@gmail.com");
//             // accountRequest5.setContactNo("092039031");
//             // accountRequest5.setDivisionId(division2.getId());
//             // accountRequest5.setIsActive(true);
//             // accountRequest5.setRoleId(role3.getId());
            
//             // Accounts account5 = accountService.addAccount(null,accountRequest5);

//             // AccountRequest accountRequest6 = new AccountRequest();
//             // accountRequest6.setUsername("dodo");
//             // accountRequest6.setFullname("Dodo Sulaiman");
//             // accountRequest6.setPassword("pass123");
//             // accountRequest6.setEmail("dodo@gmail.com");
//             // accountRequest6.setContactNo("092039031");
//             // accountRequest6.setDivisionId(division2.getId());
//             // accountRequest6.setIsActive(true);
//             // accountRequest6.setRoleId(role3.getId());
            
//             // Accounts account6 = accountService.addAccount(null,accountRequest6);

//             // Teams team = new Teams();
//             // team.setName("SMILE");
//             // team.setSupervisor(account4);
//             // teamRepository.save(team);

//             // Members member1 = new Members();
//             // member1.setTeam(team);
//             // member1.setDeveloper(account2);
//             // memberRepository.save(member1);

//             // Notifications notification2 = new Notifications();
//             // notification2.setReceiver(account);
//             // notification2.setCreatedAt(OffsetDateTime.now());
//             // notification2.setTitle("Ticket Reassigned to New Developer");
//             // notification2.setBody("Your ticket has been reassigned to a new developer, check it out right here");
//             // notificationRepository.save(notification2);

//             // Notifications notification3 = new Notifications();
//             // notification3.setReceiver(account);
//             // notification3.setCreatedAt(OffsetDateTime.now());
//             // notification3.setTitle("Ticket Resolved by Developer");
//             // notification3.setBody("Your ticket has been resolved by a developer, check it out right here");
//             // notificationRepository.save(notification3);

//             // Notifications notification4 = new Notifications();
//             // notification4.setReceiver(account);
//             // notification4.setCreatedAt(OffsetDateTime.now());
//             // notification4.setTitle("Ticket Dropped by Developer");
//             // notification4.setBody("Your ticket has been dropped by a developer, check it out right here");
//             // notificationRepository.save(notification4);

//             // Notifications notification5 = new Notifications();
//             // notification5.setReceiver(account);
//             // notification5.setCreatedAt(OffsetDateTime.now());
//             // notification5.setTitle("Ticket Taken by Developer");
//             // notification5.setBody("Your ticket has been taken by a developer, check it out right here");
//             // notificationRepository.save(notification5);


//             // for(int i=0;i<12;i++){
//             //     Random random = new Random();
//             //     int n = random.nextInt(4)+1;
                
//             //     for(int j=0;j<n;j++){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status4);
//             //         ticketRepository.save(ticket);
//             //     }
//             //     if(i%2 == 0){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status5);
//             //         ticketRepository.save(ticket);
//             //     }
//             // }

//             // for(int i=0;i<12;i++){
//             //     Random random = new Random();
//             //     int n = random.nextInt(4)+1;
                
//             //     for(int j=0;j<n;j++){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application2);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status4);
//             //         ticketRepository.save(ticket);
//             //     }
//             //     if(i%2 == 0){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application2);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status5);
//             //         ticketRepository.save(ticket);
//             //     }
//             // }

//             // for(int i=0;i<12;i++){
//             //     Random random = new Random();
//             //     int n = random.nextInt(3)+1;
                
//             //     for(int j=0;j<n;j++){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application3);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status4);
//             //         ticketRepository.save(ticket);
//             //     }
//             //     if(i%2 == 0){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application3);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status5);
//             //         ticketRepository.save(ticket);
//             //     }
//             // }

//             // for(int i=0;i<12;i++){
//             //     Random random = new Random();
//             //     int n = random.nextInt(2)+1;
                
//             //     for(int j=0;j<n;j++){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application4);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status4);
//             //         ticketRepository.save(ticket);
//             //     }
//             //     if(i%2 == 0){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application4);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status5);
//             //         ticketRepository.save(ticket);
//             //     }
//             // }

//             // for(int i=0;i<12;i++){
//             //     Random random = new Random();
//             //     int n = random.nextInt(2)+1;
                
//             //     for(int j=0;j<n;j++){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application5);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status4);
//             //         ticketRepository.save(ticket);
//             //     }
//             //     if(i%2 == 0){
//             //         Tickets ticket = new Tickets();
//             //         ticket.setDateAdded(OffsetDateTime.now().withMonth(i+1).minusYears(1));
//             //         ticket.setApplication(application5);
//             //         ticket.setReporter(account);
//             //         ticket.setTitle("Aplikasi CAMS error");
//             //         ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //         ticket.setStatus(status5);
//             //         ticketRepository.save(ticket);
//             //     }
//             // }

//             // for(int i=0;i<2;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(1));
//             //     ticket.setApplication(application5);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status5);
//             //     ticketRepository.save(ticket);
//             // }

//             // for(int i=0;i<5;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(2));
//             //     ticket.setApplication(application);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status4);
//             //     ticketRepository.save(ticket);
//             // }

//             // for(int i=0;i<4;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(3));
//             //     ticket.setApplication(application2);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status4);
//             //     ticketRepository.save(ticket);
//             // }

//             // for(int i=0;i<5;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(4));
//             //     ticket.setApplication(application3);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status4);
//             //     ticketRepository.save(ticket);
//             // }

//             // for(int i=0;i<3;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(5));
//             //     ticket.setApplication(application2);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status4);
//             //     ticketRepository.save(ticket);
//             // }

//             // for(int i=0;i<2;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(6));
//             //     ticket.setApplication(application4);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status4);
//             //     ticketRepository.save(ticket);
//             // }

//             // for(int i=0;i<5;i++){
//             //     Tickets ticket = new Tickets();
//             //     ticket.setDateAdded(OffsetDateTime.now().withMonth(7));
//             //     ticket.setApplication(application);
//             //     ticket.setReporter(account);
//             //     ticket.setTitle("Aplikasi CAMS error");
//             //     ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             //     ticket.setStatus(status4);
//             //     ticketRepository.save(ticket);
//             // }

//             // Tickets ticket = new Tickets();
//             // ticket.setDateAdded(OffsetDateTime.now());
//             // ticket.setApplication(application);
//             // ticket.setReporter(account);
//             // ticket.setTitle("Aplikasi CAMS error");
//             // ticket.setDescription("Form gabisa diisi lagi gimana dong");
//             // ticket.setStatus(status2);
//             // ticketRepository.save(ticket);

//             // Supports support = new Supports();
//             // support.setTicket(ticket);
//             // support.setDateTaken(OffsetDateTime.now());
//             // support.setDeveloper(account2);
//             // support.setIsActive(true);
//             // supportRepository.save(support);

//             // Notifications notification = new Notifications();
//             // notification.setReceiver(ticket.getReporter());
//             // notification.setCreatedAt(OffsetDateTime.now());
//             // notification.setTitle("Ticket Taken by Developer");
//             // notification.setBody("Your ticket has been taken by a developer");
//             // notification.setData("{\"ticketNo\":\""+ticket.getTicketNo()+"\",\"ticketId\":\""+ticket.getTicketId()+"\"}");
//             // notificationRepository.save(notification);

//             // Tickets ticket2 = new Tickets();
//             // ticket2.setDateAdded(OffsetDateTime.now());
//             // ticket2.setApplication(application2);
//             // ticket2.setReporter(account);
//             // ticket2.setTitle("Smile Bug pada Form Tidak ada alamat");
//             // ticket2.setDescription("Form gabisa diisi lagi gimana dong");
//             // ticket2.setStatus(status2);
//             // ticketRepository.save(ticket2);

//             // Supports support2 = new Supports();
//             // support2.setTicket(ticket2);
//             // support2.setDateTaken(OffsetDateTime.now());
//             // support2.setDeveloper(account2);
//             // support2.setIsActive(true);
//             // supportRepository.save(support2);

//             // Notifications notification2 = new Notifications();
//             // notification2.setReceiver(ticket2.getReporter());
//             // notification2.setCreatedAt(OffsetDateTime.now());
//             // notification2.setTitle("Ticket Taken by Developer");
//             // notification2.setBody("Your ticket has been taken by a developer");
//             // notification2.setData("{\"ticketNo\":\""+ticket2.getTicketNo()+"\",\"ticketId\":\""+ticket2.getTicketId()+"\"}");
//             // notificationRepository.save(notification2);

//             // Tickets ticket3 = new Tickets();
//             // ticket3.setDateAdded(OffsetDateTime.now().minusWeeks(1));
//             // ticket3.setApplication(application3);
//             // ticket3.setReporter(account);
//             // ticket3.setTitle("App Geospacial Tidak bisa Input Data Perumahan");
//             // ticket3.setDescription("Form gabisa diisi lagi gimana dong");
//             // ticket3.setStatus(status2);
//             // ticketRepository.save(ticket3);

//             // Supports support3 = new Supports();
//             // support3.setTicket(ticket3);
//             // support3.setDateTaken(OffsetDateTime.now().minusWeeks(1));
//             // support3.setDeveloper(account2);
//             // support3.setIsActive(true);
//             // supportRepository.save(support3);

//             // Tickets ticket4 = new Tickets();
//             // ticket4.setDateAdded(OffsetDateTime.now().minusMonths(1));
//             // ticket4.setApplication(application2);
//             // ticket4.setReporter(account);
//             // ticket4.setTitle("App Smile Tidak bisa Input Data Perumahan");
//             // ticket4.setDescription("Form gabisa diisi lagi gimana dong");
//             // ticket4.setStatus(status2);
//             // ticketRepository.save(ticket4);

//             // Supports support4 = new Supports();
//             // support4.setTicket(ticket4);
//             // support4.setDateTaken(OffsetDateTime.now().minusMonths(1));
//             // support4.setDeveloper(account2);
//             // support4.setIsActive(true);
//             // supportRepository.save(support4);

//             // Tickets ticket5 = new Tickets();
//             // ticket5.setDateAdded(OffsetDateTime.now());
//             // ticket5.setApplication(application2);
//             // ticket5.setReporter(account);
//             // ticket5.setTitle("App Smile Tidak bisa Input Data Perumahan");
//             // ticket5.setDescription("Form gabisa diisi lagi gimana dong");     
//             // ticket5.setStatus(status1);
//             // ticketRepository.save(ticket5);

//             // Comments comment = new Comments();
//             // comment.setTicket(ticket3);
//             // comment.setAuthor(account);
//             // comment.setBody("Tolong ini gimana caranya saya bener-bener bingung gatau harus gimana lagi");
//             // comment.setCreatedAt(OffsetDateTime.now());
//             // comment.setIsActive(true);
//             // commentRepository.save(comment);

//         };
//     }
// }
