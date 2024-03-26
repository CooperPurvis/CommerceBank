package org.example.commercebank.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/** Definition for an ip-entry. It has a unique id for each connection, as well as host name
 *  and ip address for the source as well as destination. There is a port number for the
 *  destination as well. Lastly is an ip status(active, inactive) It is also accompanied
 *  by created_by, created_at, modified_by, and modified_at. */
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ip_entry")
public class IpEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long server_info_uid;

    @Column(nullable = false)
    private Long app_info_uid;

    @Column(length = 253, nullable = false)
    private String source_host_name;

    @Column(length = 15, nullable = false)
    private String source_ip_address;

    @Column(length = 253, nullable = false)
    private String destination_host_name;

    @Column(length = 15, nullable = false)
    private String destination_ip_address;

    @Column(length = 5, nullable = false)
    private String destination_port;

    @Column(length = 8, nullable = false)
    private String ip_status;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(length = 30, nullable = false)
    private String created_by;

    @UpdateTimestamp
    private LocalDateTime modified_at;

    @Column(length = 30, nullable = false)
    private String modified_by;

    //Constructor for our use
    IpEntry(String source_host_name, String source_ip_address, String destination_host_name, String destination_ip_address,
            String destination_port, String ip_status, String created_by, String modified_by) {
        this.source_host_name = source_host_name;
        this.source_ip_address = source_ip_address;
        this.destination_host_name = destination_host_name;
        this.destination_ip_address = destination_ip_address;
        this.destination_port = destination_port;
        this.ip_status = ip_status;
        this.created_by = created_by;
        this.modified_by = modified_by;
    }
}
