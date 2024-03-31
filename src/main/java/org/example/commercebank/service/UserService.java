package org.example.commercebank.service;

import org.example.commercebank.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(String userId);
    Map<String, String> getLoginInfo(Map<String, String> loginInfo);
    String getUserRole(String userId);
    boolean exists(String userId);
    boolean isValidLogin(Map<String, String> loginInfo);
}
