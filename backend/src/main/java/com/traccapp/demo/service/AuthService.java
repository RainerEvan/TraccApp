package com.traccapp.demo.service;

import java.util.Date;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.payload.request.LoginRequest;
import com.traccapp.demo.payload.response.JwtResponse;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.security.details.UserDetailsImpl;
import com.traccapp.demo.security.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        if(!accountRepository.existsByUsername(loginRequest.getUsername())){
            throw new IllegalStateException("Invalid credentials");
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(
            token, 
            new Date((new Date()).getTime() + jwtUtils.getJwtExpirationMs()), 
            userDetails.getUsername()
        );
    }
    
}
