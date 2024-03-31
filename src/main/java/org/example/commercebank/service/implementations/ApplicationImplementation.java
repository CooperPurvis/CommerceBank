package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.Application;
import org.example.commercebank.repository.ApplicationRepository;
import org.example.commercebank.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationImplementation implements ApplicationService {

    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application createApplication(Application newApp) {
        newApp.setModifiedBy(newApp.getCreatedBy());
        return applicationRepository.save(newApp);
    }

    @Override
    public Application updateApplication(Application newApp) {
        Application oldApp = applicationRepository.getByApplicationId(newApp.getApplicationId());
        newApp.setApplicationUid(oldApp.getApplicationUid());
        if(newApp.getApplicationDescription() == null)
            newApp.setApplicationDescription(oldApp.getApplicationDescription());
        newApp.setModifiedBy(newApp.getCreatedBy());
        newApp.setCreatedAt(oldApp.getCreatedAt());
        newApp.setCreatedBy(oldApp.getCreatedBy());
        return applicationRepository.save(newApp);
    }

    @Override
    public void deleteApplication(String appId) {
        applicationRepository.delete(applicationRepository.getByApplicationId(appId));
    }

    @Override
    public boolean exists(String appId) {
        return applicationRepository.existsByApplicationId(appId);
    }

}
