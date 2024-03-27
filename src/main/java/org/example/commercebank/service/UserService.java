package org.example.commercebank.service;

import org.example.commercebank.domain.User;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(String userId);

}
