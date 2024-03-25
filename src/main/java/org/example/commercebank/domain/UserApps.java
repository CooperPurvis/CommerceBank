package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserApps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_apps_uid;
    private long user_uid;
    private String app_uid;


    //Connections to other Entities


}
