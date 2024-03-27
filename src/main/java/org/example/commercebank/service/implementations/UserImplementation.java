package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.repository.UserRepository;
import org.example.commercebank.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserImplementation implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setModifiedBy(user.getCreatedBy());
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
    public void deleteUser(String userId) {
        User oldUser = userRepository.getByUserId(userId);
        userRepository.delete(oldUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
