package com.traccapp.demo.controller;

import java.util.UUID;

import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.payload.request.AccountRequest;
import com.traccapp.demo.service.AccountService;
import com.traccapp.demo.utils.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Object> addAccount(@RequestPart(name="image", required = false) MultipartFile image, @RequestPart("account") AccountRequest accountRequest){
        try{
            Accounts account = accountService.addAccount(image, accountRequest);

            return ResponseHandler.generateResponse("Account has been created successfully!", HttpStatus.OK, account.getUsername());

        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Object> editAccount(@RequestPart(name="image", required = false) MultipartFile image, @RequestPart("accountId") UUID accountId, @RequestPart("account") AccountRequest accountRequest){
        try{
            Accounts account = accountService.editAccount(image, accountId, accountRequest);

            return ResponseHandler.generateResponse("Account has been updated successfully!", HttpStatus.OK, account.getUsername());

        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "/changePassword")
    public ResponseEntity<Object> changePassword(@RequestPart("currentPassword") String currentPassword, @RequestPart("newPassword") String newPassword){
        try{
            accountService.changePassword(currentPassword, newPassword);

            return ResponseHandler.generateResponse("Password has been updated successfully", HttpStatus.OK, null);

        } catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "/profileImg/{accountId}")
    public byte[] getProfileImg(@PathVariable("accountId") UUID accountId){
        return accountService.getProfileImage(accountId);
    }
}
