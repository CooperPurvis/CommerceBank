package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.UserApplication;
import org.example.commercebank.repository.UserApplicationRepository;
import org.example.commercebank.service.UserApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserApplicationImplementation implements UserApplicationService {
    private UserApplicationRepository userApplicationRepository;

    public List<UserApplication> getUserApplications() {
        return userApplicationRepository.findAll();
    }
    public UserApplication createUserApplication(Map<String, String> userApplicationInformation) {

    }
    public UserApplication updateUserApplication(Map<String, String> userApplicationInformation) {

    }
    public void deleteUserApplication(Map<String, String> userApplicationInformation) {

    }
}
