package org.example.commercebank.service;

import org.example.commercebank.domain.IpEntry;

import java.util.List;

public interface IpEntryService {
    List<IpEntry> getIpEntries();
    IpEntry addIpEntry(IpEntry ipEntry);
    IpEntry updateIpEntry(IpEntry ipEntry);
    void deleteIpEntry(Long ipEntryUid);
    boolean ipEntryUidNotExists(Long ipEntryUid);
    boolean infoExists(IpEntry ipEntry);
    boolean infoMissing(IpEntry ipEntry);
    boolean applicationNotExists(String applicationId);
}
