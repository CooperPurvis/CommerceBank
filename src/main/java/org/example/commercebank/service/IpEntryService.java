package org.example.commercebank.service;

import org.example.commercebank.domain.IpEntry;

import java.util.List;
import java.util.Map;

public interface IpEntryService {

    List<IpEntry> getIpEntries();
    IpEntry addIpEntry(Map<String, String> ipEntryInfo);
    IpEntry updateIpEntry(Map<String, String> ipEntryInfo);
    void deleteIpEntry(Map<String, Long> ipEntryInfo);
}
