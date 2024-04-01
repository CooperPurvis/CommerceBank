package org.example.commercebank.service;

import org.example.commercebank.domain.IpEntry;

import java.util.Map;

public interface IpEntryService {

    //IpEntry addIpEntry(IpEntry ipEntry);

    IpEntry addIpEntry(Map<String, String> ipEntryInfo);
    Long getAppUid(String appId);
}
