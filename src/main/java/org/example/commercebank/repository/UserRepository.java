package org.example.commercebank.repository;

import org.example.commercebank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query (value = "UPDATE User u SET u.userPassword = ?2 WHERE u.userId = ?1")
    User updatePassword(String id, String password);

    void deleteByUserId(String id);
}
