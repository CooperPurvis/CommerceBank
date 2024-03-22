package org.example.commercebank.service;

import org.example.commercebank.domain.UserInfo;
import org.example.commercebank.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {

    private final UserInfoRepository userInfoRepository;


    public UserInfo create(UserInfo user){
        return userInfoRepository.save(user);
    }

}
