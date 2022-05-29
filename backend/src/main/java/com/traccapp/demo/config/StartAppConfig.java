// package com.traccapp.demo.config;

// import java.util.Set;

// import com.traccapp.demo.data.ERoles;
// import com.traccapp.demo.data.EStatus;
// import com.traccapp.demo.model.Divisions;
// import com.traccapp.demo.model.Roles;
// import com.traccapp.demo.model.Status;
// import com.traccapp.demo.repository.RoleRepository;
// import com.traccapp.demo.repository.StatusRepository;
// import com.traccapp.demo.service.AccountService;
// import com.traccapp.demo.service.ApplicationService;
// import com.traccapp.demo.service.DivisionService;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class StartAppConfig {
//     @Bean
//     CommandLineRunner commandLineRunner(AccountService accountService, ApplicationService applicationService, DivisionService divisionService, RoleRepository roleRepository, StatusRepository statusRepository){
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

//             applicationService.addApplication("CAMS");
//             Divisions division = divisionService.addDivision("ITXB");
            
//             accountService.addAccount(
//                 "rainerevan", 
//                 "pass123", 
//                 "rainerevan@gmail.com", 
//                 "098303930", 
//                 division.getId(),
//                 true,
//                 Set.of(ERoles.USER)
//             );

//             accountService.addAccount(
//                 "maman", 
//                 "pass123", 
//                 "maman@gmail.com", 
//                 "098303930", 
//                 division.getId(),
//                 true,
//                 Set.of(ERoles.USER)
//             );
//         };
//     }
// }
