package org.example.commercebank.service;

import org.example.commercebank.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(String userId);

    List<User> getAllUsers();

    Map<String, String> getLoginInfo(Map<String, String> loginInfo);

    boolean isValidLogin(Map<String, String> loginInfo);
    String getUserRole(String userId);

    boolean exists(String userId);

}
