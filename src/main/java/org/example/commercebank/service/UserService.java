package org.example.commercebank.service;

import org.example.commercebank.domain.User;

public interface UserService {
    User createUser(User user);
    void deleteUser(String user_id);

}
