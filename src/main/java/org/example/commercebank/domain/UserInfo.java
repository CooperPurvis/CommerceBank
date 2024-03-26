package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Definition for a User in this system. It includes their identifying id, a user_id for
 *  logging in purposes, user_password, and a user_role (user/admin). It is also
 *  accompanied by created_by, created_at, modified_by, and modified_at.*/
@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_uid;

    @Column(length = 30, nullable = false)
    private String user_id;

    @Column(length = 45)
    private String user_password;

    @Column(length = 10, nullable = false)
    private String user_role;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(length = 30, nullable = false)
    private String created_by;

    @UpdateTimestamp
    private LocalDateTime modified_at;

    @Column(length = 30, nullable = false)
    private String modified_by;

    //Constructor we will use
    UserInfo(String user_id, String user_password, String user_role, String created_by, String modified_by) {
        this.user_id = user_id;
        this.user_password = user_password;
        this.user_role = user_role;
        this.created_by = created_by;
        this.modified_by = modified_by;
    }

    //Add Foreign Key reference to the UserApps Bridge Table
    @OneToMany(mappedBy = "user_uid")
    private List<UserApps> userAppsList = new ArrayList<>();
}
