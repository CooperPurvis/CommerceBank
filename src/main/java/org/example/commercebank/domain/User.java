package org.example.commercebank.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Definition for a User in this system. It includes their identifying id, a userId for
 *  logging in purposes, userPassword, and a user_role (user/admin). It is also
 *  accompanied by createdBy, created_at, modifiedBy, and modified_at.*/
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_uid")
    private Long userUid;

    @Column(length = 30, nullable = false, unique = true, name = "user_id")
    private String userId;

    @Column(length = 45, name = "user_password")
    private String userPassword;

    @Column(length = 10, nullable = false, name = "user_role")
    private String userRole = "user";

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(length = 30, nullable = false, name = "created_by")
    private String createdBy = "";

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(length = 30, nullable = false, name = "modified_by")
    private String modifiedBy = "";

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserApplication> userApplications = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("""
                userUid: %d
                userId: %s
                userPassword: %s
                userRole: %s
                createdAt: %s
                createdBy: %s
                modifiedAt: %s
                modifiedBy: %s
                """, getUserUid(), getUserId(), getUserPassword(), getUserRole(),
                getCreatedAt(), getCreatedBy(), getModifiedAt(), getModifiedBy());
    }
}
