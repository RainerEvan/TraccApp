package com.traccapp.demo.controller;

import java.util.UUID;

import com.traccapp.demo.payload.request.AccountRequest;
import com.traccapp.demo.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {
    
    @Autowired
    private final AccountService accountService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> addAccount(@RequestPart("image") MultipartFile image, @RequestPart("account") AccountRequest accountRequest){
        accountService.addAccount(image, accountRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Account has been created successfully!");
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<String> editAccount(@RequestPart("image") MultipartFile image, @RequestPart("account") AccountRequest accountRequest){
        accountService.editAccount(image, accountRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Account has been updated successfully!");
    }

    @PutMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword){
        accountService.changePassword(currentPassword, newPassword);

        return ResponseEntity.status(HttpStatus.OK).body("Password has been updated successfully");
    }

    @GetMapping(path = "/profileImg/{accountId}")
    public byte[] getProfileImg(@PathVariable("accountId") UUID accountId){
        return accountService.getProfileImage(accountId);
    }
}
