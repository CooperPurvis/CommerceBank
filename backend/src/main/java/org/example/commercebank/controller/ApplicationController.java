package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.Application;
import org.example.commercebank.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping("/api/application")
    @CrossOrigin
    public ResponseEntity<List<Application>> getApplications() {
        return new ResponseEntity<>(applicationService.getApplications(), HttpStatus.OK);
    }

    @GetMapping("/api/application/idList")
    @CrossOrigin
    public ResponseEntity<List<String>> getApplicationIds() {
        return new ResponseEntity<>(applicationService.getApplicationIds(), HttpStatus.OK);
    }

    @PostMapping("/api/application")
    @CrossOrigin
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        if(applicationService.exists(application))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(applicationService.infoMissing(application))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(applicationService.createApplication(application), HttpStatus.CREATED);
    }

    @PutMapping("/api/application")
    @CrossOrigin
    public ResponseEntity<Application> updateApplication(@RequestBody Application application) {
        if(application.getApplicationUid() == null)
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(!applicationService.exists(application))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(applicationService.infoMissing(application))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(applicationService.updateApplication(application), HttpStatus.OK);
    }

    @DeleteMapping("/api/application")
    @CrossOrigin
    public ResponseEntity<Void> deleteApplication(@RequestBody Application application) {
        if(application.getApplicationUid() == null)
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(!applicationService.exists(application))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        applicationService.deleteApplication(application);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
