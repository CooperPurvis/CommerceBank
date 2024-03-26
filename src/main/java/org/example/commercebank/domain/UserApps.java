package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/** Definition for the UserApps Bridge table between users and applications. It has a
 *  unique id as well as foreign keys to AppInfo and UserInfo. It is also accompanied
 *  by created_by, created_at, modified_by, and modified_at. */
@Data
@Entity
@NoArgsConstructor
@Table(name = "user_apps")
public class UserApps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_apps_uid;

    @Column(nullable = false)
    private long user_uid;

    @Column(nullable = false)
    private long app_info_uid;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(length = 30, nullable = false)
    private String created_by;

    @UpdateTimestamp
    private LocalDateTime modified_at;

    @Column(length = 30, nullable = false)
    private String modified_by;

    //Constructor for our use
    UserApps(String created_by, String modified_by) {
        this.created_by = created_by;
        this.modified_by = modified_by;
    }
}
