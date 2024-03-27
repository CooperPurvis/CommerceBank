package org.example.commercebank.repository;

import org.example.commercebank.domain.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
}
