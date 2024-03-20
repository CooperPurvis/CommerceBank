package com.example.CommerceBank.domain;
import lombok.Data;
import jakarta.persistence.Id;

@Data
@Entity
public class User {

    @Id
    private Long uid;
    private String userId;
    private String password;
    private String role;
}
