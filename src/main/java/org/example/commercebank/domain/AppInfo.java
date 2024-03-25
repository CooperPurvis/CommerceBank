package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class AppInfo {

    @Id
    private String app_info_uid;
    private String app_info_description;

    AppInfo(String app_info_description) {
        this.app_info_description = app_info_description;
    }
}
