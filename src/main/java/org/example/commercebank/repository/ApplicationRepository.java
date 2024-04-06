package org.example.commercebank.repository;

import org.example.commercebank.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Application getByApplicationId(String applicationId);


    boolean existsByApplicationId(String applicationId);
}
