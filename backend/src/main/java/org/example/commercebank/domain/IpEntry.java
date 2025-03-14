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
@AllArgsConstructor
@Entity
@Table(name = "ip_entries")
public class IpEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip_entry_uid")
    private Long IpEntryUid;

    @Column(length = 253, nullable = false, name = "source_house_name")
    private String sourceHostName;

    @Column(length = 15, nullable = false, name = "source_ip_address")
    private String sourceIpAddress;

    @Column(length = 253, nullable = false, name = "destination_host_name")
    private String destinationHostName;

    @Column(length = 15, nullable = false, name = "destination_ip_address")
    private String destinationIpAddress;

    @Column(length = 5, nullable = false, name = "destination_port")
    private String destinationPort;

    @Column(length = 8, nullable = false, name = "ip_status")
    private String ipStatus;

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "application_Uid", nullable = false)
    private Application application;

    @Override
    public String toString() {
        return String.format("""
                        Ip Entry:
                        --------------------------------------
                        ipEntryUid: %d
                        Referenced App: %s
                        sourceHostName: %s
                        sourceIpAddress: %s
                        destinationHostName: %s
                        destinationIpAddress: %s
                        destinationPort: %s
                        ipStatus: %s
                        createdAt: %s
                        createdBy: %s
                        modifiedAt: %s
                        modifiedBy: %s
                        --------------------------------------""", getIpEntryUid(), application.toString(),
                getSourceHostName(), getSourceIpAddress(), getDestinationHostName(), getDestinationIpAddress(),
                getDestinationPort(), getIpStatus(), getCreatedAt(), getCreatedBy(), getModifiedAt(), getModifiedBy());
    }

    public String[] toExcelRow() {
        return new String[]{application.getApplicationId(), sourceHostName, sourceIpAddress, destinationHostName,
            destinationIpAddress, destinationPort, ipStatus, createdAt.toString(), createdBy, modifiedAt.toString(), modifiedBy};
    }
}
