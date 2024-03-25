package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppInfo {


    @Id
    private String app_info_uid;
    private String app_info_description;


}
