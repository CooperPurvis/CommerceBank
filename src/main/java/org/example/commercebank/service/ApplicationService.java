package org.example.commercebank.service;

import org.example.commercebank.domain.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> getApplications();
    Application createApplication(Application application);

    Application updateApplication(Application application);

    void deleteApplication(String appId);

    boolean exists(String appId);
}
