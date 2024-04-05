package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.User;
import org.example.commercebank.repository.UserRepository;
import org.example.commercebank.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    //
    @Override
    public User updateUser(User newUser) {
        Optional<User> oldUserList = userRepository.findById(newUser.getUserUid());
        if(oldUserList.isEmpty())
            return null;
        User oldUser = oldUserList.get();
        newUser.setModifiedBy(newUser.getCreatedBy());
        newUser.setCreatedBy(oldUser.getCreatedBy());
        newUser.setCreatedAt(oldUser.getCreatedAt());
        newUser.setUserUid(oldUser.getUserUid());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(Long userUid) {
        userRepository.deleteById(userUid);
    }

    @Override
    public boolean exists(User user) {
        if(user.getUserUid() != null)
            return userRepository.existsById(user.getUserUid());
        return userRepository.existsByUserId(user.getUserId());
    }


    //Related to Logging in
    @Override
    public boolean isValidLogin(Map<String, String> userInfo) {
        return userRepository.existsByUserIdAndUserPassword(userInfo.get("userId"), userInfo.get("userPassword"));
    }

    @Override
    public User getLoginUser(Map<String, String> loginInfo) {
        return userRepository.getByUserId(loginInfo.get("userId"));
    }
}
