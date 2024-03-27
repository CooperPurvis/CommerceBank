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
    public User updateUser(User user) {
        User oldUser = userRepository.getByUserId(user.getUserId());
        if (user.getUserPassword()==null)
            user.setUserPassword(oldUser.getUserPassword());
        user.setCreatedBy(oldUser.getCreatedBy());
        user.setCreatedAt(oldUser.getCreatedAt());
        user.setUserUid(oldUser.getUserUid());
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(String userId) {
        return userRepository.deleteUserByUserId(userId);
    }

}
