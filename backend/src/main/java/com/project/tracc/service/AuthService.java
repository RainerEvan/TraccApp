package com.project.tracc.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.tracc.model.Accounts;
import com.project.tracc.payload.request.LoginRequest;
import com.project.tracc.payload.response.JwtResponse;
import com.project.tracc.repository.AccountRepository;
import com.project.tracc.security.details.UserDetailsImpl;
import com.project.tracc.security.jwt.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtils jwtUtils;

    public Accounts getCurrentAccount(){
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        
        return accountRepository.findByUsername(principal)
            .orElseThrow(() -> new UsernameNotFoundException("Account with current username cannot be found: "+principal));
    }

    public JwtResponse loginAccount(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            String token = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

            return new JwtResponse(
                token, 
                new Date((new Date()).getTime() + jwtUtils.getJwtExpirationMs()), 
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getIsActive(),
                roles
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password!");
        }
    }
}
