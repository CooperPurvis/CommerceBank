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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_uid;

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


    //Add Foreign Key reference to the UserApps Bridge Table
    @OneToMany(mappedBy = "user_uid")
    private List<UserApplication> userApplicationList = new ArrayList<>();
}
