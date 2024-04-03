package org.example.commercebank.service;

import org.example.commercebank.domain.UserApplication;

import java.util.List;
import java.util.Map;

public interface UserApplicationService {
    List<UserApplication> getUserApplications();
    UserApplication createUserApplication(Map<String, String> userApplicationInformation);
    UserApplication updateUserApplication(Map<String, String> userApplicationInformation);
    void deleteUserApplication(Map<String, String> userApplicationInformation);
}
