package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.UserApplication;
import org.example.commercebank.service.UserApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userApplication")
@AllArgsConstructor
public class UserApplicationController {
    private UserApplicationService userApplicationService;

    @GetMapping
    public ResponseEntity<List<UserApplication>> getUserApplications(){
        return new ResponseEntity<>(userApplicationService.getUserApplications(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserApplication> createUserApplication(@RequestBody Map<String, String> userAppInfo) {
        return new ResponseEntity<>(userApplicationService.createUserApplication(userAppInfo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserApplication> updateUserApplication(@RequestBody Map<String, String> userAppInfo) {
        return new ResponseEntity<>(userApplicationService.updateUserApplication(userAppInfo), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserApplication(@RequestBody Map<String, String> userAppInfo) {
        userApplicationService.deleteUserApplication(userAppInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
