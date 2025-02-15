package org.example.commercebank.service;

import org.example.commercebank.domain.UserApplication;

import java.util.List;

public interface UserApplicationService {
    List<String> getAssignedApplicationIds(UserApplication userApplication);
    UserApplication createUserApplication(UserApplication userApplication);
    void deleteUserApplication(UserApplication userApplication);
    boolean exists(UserApplication userApplication);
    boolean userOrAppNotExists(UserApplication userApplication);
    boolean userNotExists(UserApplication userApplication);
    boolean infoMissing(UserApplication userApplication);
}
