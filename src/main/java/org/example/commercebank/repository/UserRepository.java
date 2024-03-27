package org.example.commercebank.repository;

import org.example.commercebank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query (value = "SELECT u FROM User u WHERE u.userId = ?1")
            User getByUserId(String userId);


    User deleteUserByUserId(String userId);
}
