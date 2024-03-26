package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/** Definition for the Applications themselves. This includes a unique long id primary key,
 *  a 3 character id that will be used by employees, and a short description. It is also
 *  accompanied by created_by, created_at, modified_by, and modified_at. */
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long app_info_uid;

    @Column(length = 3, nullable = false)
    private String app_info_id;

    @Column(length = 50)
    private String app_info_description;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(length = 30, nullable = false)
    private String created_by;

    @UpdateTimestamp
    private LocalDateTime modified_at;

    @Column(length = 30, nullable = false)
    private String modified_by;

    //Constructor we will use
    Application(String app_info_id, String app_info_description, String created_by, String modified_by) {
        this.app_info_id = app_info_id;
        this.app_info_description = app_info_description;
        this.created_by = created_by;
        this.modified_by = modified_by;
    }


    //Add Foreign Key references to the ServerInfo list and UserApps list
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IpEntry> ipEntries = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_apps", joinColumns = @JoinColumn(name = "app_info_uid"),
                inverseJoinColumns = @JoinColumn(name = "user_uid"))
    private List<User> users = new ArrayList<>();
}
