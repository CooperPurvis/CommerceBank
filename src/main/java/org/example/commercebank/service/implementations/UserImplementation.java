package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.repository.UserRepository;
import org.example.commercebank.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserImplementation implements UserService {
    private UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User createUser(User newUser) {
        newUser.setModifiedBy(newUser.getCreatedBy());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User newUser) {
        User oldUser = userRepository.getByUserId(newUser.getUserId());
        if (newUser.getUserPassword()==null)
            newUser.setUserPassword(oldUser.getUserPassword());
        newUser.setModifiedBy(newUser.getCreatedBy());
        newUser.setCreatedBy(oldUser.getCreatedBy());
        newUser.setCreatedAt(oldUser.getCreatedAt());
        newUser.setUserUid(oldUser.getUserUid());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.delete(userRepository.getByUserId(userId));
    }

    @Override
    public boolean isValidLogin(Map<String, String> userInfo) {
        return userRepository.existsByUserIdAndUserPassword(userInfo.get("userId"), userInfo.get("userPassword"));
    }

    @Override
    public String getUserRole(String userId) {
        User user = userRepository.getByUserId(userId);
        return user.getUserRole();
    }

    @Override
    public boolean exists(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public Map<String, String> getLoginInfo(Map<String, String> loginInfo) {
        loginInfo.remove("userPassword");
        loginInfo.put("userRole", getUserRole(loginInfo.get("userId")));
        return loginInfo;
    }
}
