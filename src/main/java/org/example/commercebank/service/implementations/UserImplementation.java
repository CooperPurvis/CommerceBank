package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.repository.UserRepository;
import org.example.commercebank.service.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserImplementation implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String user_id) {
        userRepository.deleteByUserId(user_id);
    }

}
