package org.example.commercebank.service;

import org.example.commercebank.domain.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> getApplications();
    List<String> getApplicationIds();
    Application createApplication(Application application);
    Application updateApplication(Application application);
    void deleteApplication(Long appUid);
    boolean exists(Application application);
}
