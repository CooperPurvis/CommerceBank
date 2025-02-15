package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.Application;
import org.example.commercebank.repository.ApplicationRepository;
import org.example.commercebank.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationImplementation implements ApplicationService {

    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public List<String> getApplicationIds() {
        List<Application> applications = getApplications();
        List<String> applicationIds = new ArrayList<>();
        for(Application application : applications)
            applicationIds.add(application.getApplicationId());
        return applicationIds;
    }

    @Override
    public Application createApplication(Application newApp) {
        newApp.setModifiedBy(newApp.getCreatedBy());
        return applicationRepository.save(newApp);
    }

    @Override
    public Application updateApplication(Application newApp) {
        Optional<Application> oldAppList = applicationRepository.findById(newApp.getApplicationUid());
        if(oldAppList.isEmpty())
            return null;
        Application oldApp = oldAppList.get();
        newApp.setModifiedBy(newApp.getCreatedBy());
        newApp.setCreatedAt(oldApp.getCreatedAt());
        newApp.setCreatedBy(oldApp.getCreatedBy());
        return applicationRepository.save(newApp);
    }

    @Override
    public void deleteApplication(Application application) {
        applicationRepository.deleteById(application.getApplicationUid());
    }

    @Override
    public boolean exists(Application application) {
        if(application.getApplicationUid() != null)
            return applicationRepository.existsById(application.getApplicationUid());
        return applicationRepository.existsByApplicationId(application.getApplicationId());
    }

    @Override
    public boolean infoMissing(Application application) {
        return application.getApplicationId() == null || application.getCreatedBy() == null;
    }
}
