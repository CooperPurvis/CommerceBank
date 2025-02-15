package org.example.commercebank.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/** Definition for the Applications themselves. This includes a unique long id primary key,
 *  a 3 character id that will be used by employees, and a short description. It is also
 *  accompanied by createdBy, created_at, modifiedBy, and modified_at. */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_uid")
    private Long applicationUid;

    @Column(length = 3, nullable = false, unique = true, name = "application_id")
    private String applicationId;

    @Column(length = 50, name = "application_description")
    private String applicationDescription;

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

    public Application(Long applicationUid) {
        this.applicationUid = applicationUid;
    }

    //Add Foreign Key references to the ServerInfo list and UserApps list
    @JsonIgnore
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IpEntry> ipEntries = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserApplication> userApplications = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("""
                        Application:
                        -------------------------------------
                        applicationUid: %d
                        applicationId: %s
                        applicationDescription: %s
                        createdAt: %s
                        createdBy: %s
                        modifiedAt: %s
                        modifiedBy: %s
                        --------------------------------------""", applicationUid, applicationId,
                applicationDescription, createdAt, createdBy, modifiedAt, modifiedBy);
    }
}
