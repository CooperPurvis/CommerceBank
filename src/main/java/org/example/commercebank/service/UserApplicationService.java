package org.example.commercebank.service;

import org.example.commercebank.domain.UserApplication;

import java.util.List;
import java.util.Map;

public interface UserApplicationService {
    List<UserApplication> getUserApplications();
    UserApplication createUserApplication(Map<String, String> userAppInfo);
    void deleteUserApplication(Map<String, String> userAppInfo);
    boolean exists(Map<String, String> userAppInfo);
    boolean userAndAppExists(Map<String, String> userAppInfo);
}
