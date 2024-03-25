package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IpEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long server_info_uid;
    private String app_info_uid; //?
    private String source_host_name;
    private String source_ip_address;
    private String destination_host_name;
    private String destination_ip_address;
    private String destination_port;
    private String ip_status;


    //Connections to Other Entities
}
