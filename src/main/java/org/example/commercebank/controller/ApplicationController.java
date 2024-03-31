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
@RequestMapping("/api/application")
@AllArgsConstructor
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<Application>> getApplications() {
        return new ResponseEntity<>(applicationService.getApplications(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application newApp) {
        Application savedApplication = applicationService.createApplication(newApp);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Application> updateApplication(@RequestBody Application newApp) {
        Application updatedApplication = applicationService.updateApplication(newApp);
        return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteApplication(@RequestBody Map<String, String> appInfo) {
        if(!applicationService.exists(appInfo.get("applicationId")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        applicationService.deleteApplication(appInfo.get("applicationId"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
