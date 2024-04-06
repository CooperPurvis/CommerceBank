package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.UserApplication;
import org.example.commercebank.service.UserApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/userApplication")
@AllArgsConstructor
public class UserApplicationController {
    private UserApplicationService userApplicationService;

    //Return list of Assigned Applications
    @GetMapping
    public ResponseEntity<List<String>> getUserApplications(@RequestBody UserApplication userApplication){
        if(userApplicationService.userNotExists(userApplication))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userApplicationService.getAssignedApplicationIds(userApplication), HttpStatus.OK);
    }
    //Create a User Application and return it
    @PostMapping
    public ResponseEntity<UserApplication> createUserApplication(@RequestBody UserApplication userApplication) {
        if(userApplicationService.userOrAppNotExists(userApplication))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(userApplicationService.exists(userApplication))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(userApplicationService.infoMissing(userApplication))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(userApplicationService.createUserApplication(userApplication), HttpStatus.CREATED);
    }
    //Delete a User Application if the given userId and applicationId exist in the table
    @DeleteMapping
    public ResponseEntity<Void> deleteUserApplication(@RequestBody UserApplication userApplication) {
        if(userApplicationService.userOrAppNotExists(userApplication))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!userApplicationService.exists(userApplication))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        userApplicationService.deleteUserApplication(userApplication);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
