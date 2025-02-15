package org.example.commercebank.repository;

import org.example.commercebank.domain.IpEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpEntryRepository extends JpaRepository<IpEntry, Long> {
    //Check for an existing Ip Entry from all unique information
    boolean existsBySourceHostNameAndSourceIpAddressAndDestinationHostNameAndDestinationIpAddressAndDestinationPort
            (String sourceHostName, String sourceIpAddress, String destinationHostName, String destinationIpAddress, String destinationPort);

    //Get an existing Ip Entry from its unique information
    IpEntry getBySourceHostNameAndSourceIpAddressAndDestinationHostNameAndDestinationIpAddressAndDestinationPort
            (String sourceHostName, String sourceIpAddress, String destinationHostName, String destinationIpAddress, String destinationPort);
}
