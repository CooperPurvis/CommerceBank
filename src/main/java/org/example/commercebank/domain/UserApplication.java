package org.example.commercebank.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    public UserApplication(String createdBy) {
        this.createdBy = createdBy;
    }


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "application_uid", nullable = false)
    private Application application;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "user_uid", nullable = false)
    private User user;

    public String toString() {
        return String.format("""
                User Application Link
                ------------------------------------
                userApplicationUid: %d
                userId: %s
                applicationId: %s
                createdAt: %s
                createdBy: %s
                ------------------------------------""", userAppsUid, user.toString(), application.toString(),
                createdAt, createdBy);
    }
}
