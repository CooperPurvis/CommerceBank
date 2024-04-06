package org.example.commercebank.repository;

import org.example.commercebank.domain.Application;
import org.example.commercebank.domain.User;
import org.example.commercebank.domain.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
    UserApplication getByUserAndApplication(User user, Application application);
    boolean existsByUserAndApplication(User user, Application application);

    //void deleteUserApplicationByApplicationAndUser(Application application, User user);
    void deleteUserApplicationByUserAppsUid(Long userAppsUid);

    void deleteUserApplicationByUserAndApplication(User user, Application application);

    List<UserApplication> getUserApplicationsByUserAndApplication(User user, Application application);
}
