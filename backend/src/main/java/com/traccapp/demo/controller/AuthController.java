package com.traccapp.demo.controller;

import com.traccapp.demo.payload.request.LoginRequest;
import com.traccapp.demo.payload.response.JwtResponse;
import com.traccapp.demo.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    
    @Autowired
    public AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        JwtResponse response = authService.loginAccount(loginRequest);

        return ResponseEntity.ok(response);
    }
}
