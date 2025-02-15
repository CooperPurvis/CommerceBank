package org.example.commercebank.repository;

import org.example.commercebank.domain.Application;
import org.example.commercebank.domain.User;
import org.example.commercebank.domain.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    //Get all User Applications linked to a given User
    List<UserApplication> findAllByUser(User user);

    //Get a User Application from a given user and application
    UserApplication getByUserAndApplication(User user, Application application);

    //Check for an existing User Application by the given user and application
    boolean existsByUserAndApplication(User user, Application application);
}
