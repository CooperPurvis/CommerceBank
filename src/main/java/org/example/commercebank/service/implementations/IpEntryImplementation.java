package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.Application;
import org.example.commercebank.domain.IpEntry;
import org.example.commercebank.repository.ApplicationRepository;
import org.example.commercebank.repository.IpEntryRepository;
import org.example.commercebank.service.IpEntryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class IpEntryImplementation implements IpEntryService {
    private IpEntryRepository ipEntryRepository;
    private ApplicationRepository applicationRepository;

    @Override
    public List<IpEntry> getIpEntries() {
        return ipEntryRepository.findAll();
    }

    @Override
    public IpEntry addIpEntry(Map<String, String> ipEntryInfo) {
        Application referencedApp = applicationRepository.getByApplicationId(ipEntryInfo.get("applicationId"));
        IpEntry newIpEntry = new IpEntry(ipEntryInfo.get("sourceHostName"), ipEntryInfo.get("sourceIpAddress"),
                ipEntryInfo.get("destinationHostName"), ipEntryInfo.get("destinationIpAddress"),
                ipEntryInfo.get("destinationPort"), ipEntryInfo.get("createdBy"), referencedApp);
        if(ipEntryInfo.get("ipStatus") != null)
            newIpEntry.setIpStatus(ipEntryInfo.get("ipStatus"));
        System.out.println("IpEntry:\n" + newIpEntry);
        return ipEntryRepository.save(newIpEntry);
    }

    @Override
    public IpEntry updateIpEntry(Map<String, String> ipEntryInfo) {
        return null;
    }

    @Override
    public void deleteIpEntry(Map<String, Long> ipEntryInfo) {
        ipEntryRepository.deleteById(ipEntryInfo.get("ipEntryUid"));
    }
}
