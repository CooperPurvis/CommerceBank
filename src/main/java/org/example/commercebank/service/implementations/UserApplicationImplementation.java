package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.Application;
import org.example.commercebank.domain.User;
import org.example.commercebank.domain.UserApplication;
import org.example.commercebank.repository.ApplicationRepository;
import org.example.commercebank.repository.UserApplicationRepository;
import org.example.commercebank.repository.UserRepository;
import org.example.commercebank.service.UserApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserApplicationImplementation implements UserApplicationService {
    private UserApplicationRepository userApplicationRepository;
    private UserRepository userRepository;
    private ApplicationRepository applicationRepository;

    @Override
    public List<UserApplication> getUserApplications() {
        return userApplicationRepository.findAll();
    }
    @Override
    public UserApplication createUserApplication(Map<String, String> userAppInfo) {
        UserApplication newLink = new UserApplication(userAppInfo.get("createdBy"));
        newLink.setUser(userRepository.getByUserId(userAppInfo.get("userId")));
        newLink.setApplication(applicationRepository.getByApplicationId(userAppInfo.get("applicationId")));
        return userApplicationRepository.save(newLink);
    }

    @Override
    public void deleteUserApplication(Map<String, String> userAppInfo) {
        User refUser = userRepository.getByUserId(userAppInfo.get("userId"));
        Application refApp =  applicationRepository.getByApplicationId(userAppInfo.get("applicationId"));
        //userApplicationRepository.deleteUserApplicationByApplicationAndUser(refApp, refUser);
        UserApplication oldLink = userApplicationRepository.getByUserAndApplication(refUser, refApp);
        System.out.println(oldLink.toString());
        userApplicationRepository.deleteById(oldLink.getUserAppsUid());
    }

    @Override
    public boolean exists(Map<String, String> userAppInfo) {
        return userApplicationRepository.existsByUserAndApplication(
                userRepository.getByUserId(userAppInfo.get("userId")),
                applicationRepository.getByApplicationId(userAppInfo.get("applicationId"))
        );
    }

    @Override
    public boolean userAndAppExists(Map<String, String> userAppInfo) {
        return (userRepository.existsByUserId(userAppInfo.get("userId")) &&
                applicationRepository.existsByApplicationId(userAppInfo.get("applicationId")));
    }
}
