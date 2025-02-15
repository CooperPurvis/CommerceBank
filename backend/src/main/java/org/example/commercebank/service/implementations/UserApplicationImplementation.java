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

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserApplicationImplementation implements UserApplicationService {
    private UserApplicationRepository userApplicationRepository;
    private UserRepository userRepository;
    private ApplicationRepository applicationRepository;

    //Return a list of User Applications
    @Override
    public List<String> getAssignedApplicationIds(UserApplication userApplication) {
        List<UserApplication> matchingUserApps = userApplicationRepository.findAllByUser(
                userRepository.getByUserId(userApplication.getUser().getUserId()));
        List<String> assignedIds = new ArrayList<>();
        for(UserApplication matchingUserApp : matchingUserApps)
            assignedIds.add(matchingUserApp.getApplication().getApplicationId());
        return assignedIds;
    }

    //Create a User Application with the required information
    @Override
    public UserApplication createUserApplication(UserApplication userApplication) {
        userApplication.setUser(userRepository.getByUserId(userApplication.getUser().getUserId()));
        userApplication.setApplication(applicationRepository.getByApplicationId(userApplication.getApplication().getApplicationId()));
        return userApplicationRepository.save(userApplication);
    }

    //Delete the User Application based off the given user and application Ids
    @Override
    public void deleteUserApplication(UserApplication userApplication) {
        User refUser = userRepository.getByUserId(userApplication.getUser().getUserId());
        Application refApp =  applicationRepository.getByApplicationId(userApplication.getApplication().getApplicationId());
        UserApplication oldLink = userApplicationRepository.getByUserAndApplication(refUser, refApp);
        userApplicationRepository.deleteById(oldLink.getUserAppsUid());
    }

    //Check for an existing User Application with the given user and application Ids
    @Override
    public boolean exists(UserApplication userApplication) {
        return userApplicationRepository.existsByUserAndApplication(
                userRepository.getByUserId(userApplication.getUser().getUserId()),
                applicationRepository.getByApplicationId(userApplication.getApplication().getApplicationId())
        );
    }

    //Check for an existing user and application based off the given Ids
    @Override
    public boolean userOrAppNotExists(UserApplication userApplication) {
        return (!userRepository.existsByUserId(userApplication.getUser().getUserId()) ||
                !applicationRepository.existsByApplicationId(userApplication.getApplication().getApplicationId()));
    }

    @Override
    public boolean userNotExists(UserApplication userApplication) {
        return !userRepository.existsByUserId(userApplication.getUser().getUserId());
    }

    @Override
    public boolean infoMissing(UserApplication userApplication) {
        return userApplication.getCreatedBy() == null;
    }
}
