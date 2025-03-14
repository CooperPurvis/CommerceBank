package org.example.commercebank.service;

import org.example.commercebank.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    boolean exists(User user);
    boolean infoMissing(User user);
    boolean isValidLogin(Map<String, String> loginInfo);
    User getLoginUser(Map<String, String> loginInfo);
}
