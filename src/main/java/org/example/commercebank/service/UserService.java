package org.example.commercebank.service;

import org.example.commercebank.domain.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(String userId);

    List<User> getAllUsers();

}
