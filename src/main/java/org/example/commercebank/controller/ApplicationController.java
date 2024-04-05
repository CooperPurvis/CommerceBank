package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.Application;
import org.example.commercebank.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping("/api/application")
    public ResponseEntity<List<Application>> getApplications() {
        return new ResponseEntity<>(applicationService.getApplications(), HttpStatus.OK);
    }

    @GetMapping("/api/application/idList")
    public ResponseEntity<List<String>> getApplicationIds() {
        return new ResponseEntity<>(applicationService.getApplicationIds(), HttpStatus.OK);
    }

    @PostMapping("/api/application")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        if(applicationService.exists(application))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(applicationService.createApplication(application), HttpStatus.CREATED);
    }

    @PutMapping("/api/application")
    public ResponseEntity<Application> updateApplication(@RequestBody Application application) {
        if(!applicationService.exists(application))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(applicationService.updateApplication(application), HttpStatus.OK);
    }

    @DeleteMapping("/api/application")
    public ResponseEntity<Void> deleteApplication(@RequestBody Map<String, Long> appInfo) {
        if(!applicationService.exists(new Application(appInfo.get("applicationUid"))))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        applicationService.deleteApplication(appInfo.get("applicationUid"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
