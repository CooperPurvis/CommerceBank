package org.example.commercebank.service;


import org.example.commercebank.domain.AppInfo;
import org.example.commercebank.domain.UserInfo;
import org.example.commercebank.repository.AppInfoRepository;
import org.example.commercebank.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppInfoService {

    private final UserInfoRepository userInfoRepository;
    private final AppInfoRepository appInfoRepository;
    public AppInfo create(AppInfo appInfo, String userId) {

        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        appInfo.setUserInfo(userInfo);

        System.out.println("  --- user --- ");
        System.out.println("ID " + userInfo.getUserId());
        System.out.println("Password " + userInfo.getPassword());
        System.out.println("Role " + userInfo.getRole());
        return appInfoRepository.save(appInfo);


    }

}
