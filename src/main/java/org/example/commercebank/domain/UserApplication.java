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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_apps")
public class UserApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_apps_uid")
    private Long userAppsUid;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(length = 30, nullable = false, name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(length = 30, nullable = false, name = "modified_by")
    private String modifiedBy;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_uid", nullable = false)
    private Application application;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_uid", nullable = false)
    private User user;
}
