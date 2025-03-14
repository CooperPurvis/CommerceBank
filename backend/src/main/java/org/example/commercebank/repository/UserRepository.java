package org.example.commercebank.repository;

import org.example.commercebank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUserId(String userId);
    boolean existsByUserIdAndUserPassword(String userId, String userPassword);
    boolean existsByUserId(String userId);
}
