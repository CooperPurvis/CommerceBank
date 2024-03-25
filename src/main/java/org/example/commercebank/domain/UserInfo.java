package org.example.commercebank.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_uid;
    private String user_id;
    private String user_password;
    private String user_role;


    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    private List<AppInfo> appInfo = new ArrayList<>();

}
