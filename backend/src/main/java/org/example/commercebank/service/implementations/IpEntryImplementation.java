package org.example.commercebank.service.implementations;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.IpEntry;
import org.example.commercebank.repository.ApplicationRepository;
import org.example.commercebank.repository.IpEntryRepository;
import org.example.commercebank.service.IpEntryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IpEntryImplementation implements IpEntryService {
    private IpEntryRepository ipEntryRepository;
    private ApplicationRepository applicationRepository;

    @Override
    // Return a list of all Ip Entries in the Database
    public List<IpEntry> getIpEntries(List<Long> ipEntryUids) {
        if(ipEntryUids.isEmpty())
            return ipEntryRepository.findAll();
        return ipEntryRepository.findAllById(ipEntryUids);
    }

    @Override
    // Add an Ip Entry to the Database
    public IpEntry addIpEntry(IpEntry ipEntry) {
        //Set its application based on a supplied applicationId
        ipEntry.setApplication(applicationRepository.getByApplicationId(ipEntry.getApplication().getApplicationId()));

        //Set default ipStatus if not provided, and set modifiedBy to match createdBy
        if(ipEntry.getIpStatus() == null)
            ipEntry.setIpStatus("Inactive");
        ipEntry.setModifiedBy(ipEntry.getCreatedBy());

        //Save new Ip Entry and return
        return ipEntryRepository.save(ipEntry);
    }

    @Override
    //Update an Existing Ip Entry using the Uid to update information as necessary
    public IpEntry updateIpEntry(IpEntry ipEntry) {
        //Set Ip Entry's application based on supplied applicationId
        ipEntry.setApplication(applicationRepository.getByApplicationId(ipEntry.getApplication().getApplicationId()));

        //Set default ipStatus
        if(ipEntry.getIpStatus() == null)
            ipEntry.setIpStatus("Inactive");

        //Get the existing Ip Entry and update information as needed
        Optional<IpEntry> oldIpEntryReturn = ipEntryRepository.findById(ipEntry.getIpEntryUid());
        if(oldIpEntryReturn.isEmpty())
            return null;
        IpEntry oldIpEntry = oldIpEntryReturn.get();
        ipEntry.setModifiedBy(ipEntry.getCreatedBy());
        ipEntry.setCreatedAt(oldIpEntry.getCreatedAt());
        ipEntry.setCreatedBy(oldIpEntry.getCreatedBy());
        return ipEntryRepository.save(ipEntry);
    }

    @Override
    //Delete an existing Ip Entry by the Uid
    public void deleteIpEntry(Long ipEntryUid) {
        ipEntryRepository.deleteById(ipEntryUid);
    }

    @Override
    //Returns true if the Ip Entry Uid sent doesn't exist in the database
    public boolean ipEntryUidNotExists(Long ipEntryUid) {
        return !ipEntryRepository.existsById(ipEntryUid);
    }

    @Override
    //Returns true if the information presented in a create or update already exists in an Ip Entry
    public boolean infoExists(IpEntry ipEntry) {
        //check for an existing Ip Entry
        if(ipEntry.getIpEntryUid() != null) {
            IpEntry oldIpEntryReturn = ipEntryRepository.getBySourceHostNameAndSourceIpAddressAndDestinationHostNameAndDestinationIpAddressAndDestinationPort(
                    ipEntry.getSourceHostName(), ipEntry.getSourceIpAddress(), ipEntry.getDestinationHostName(),
                    ipEntry.getDestinationIpAddress(), ipEntry.getDestinationPort()
            );

            //If a IpEntry isn't found, return false
            if(oldIpEntryReturn == null)
                return false;

            //If that found ipEntry matches Uids, return false
            return !oldIpEntryReturn.getIpEntryUid().equals(ipEntry.getIpEntryUid());
        }

        //check for a new Ip Entry
        return ipEntryRepository.existsBySourceHostNameAndSourceIpAddressAndDestinationHostNameAndDestinationIpAddressAndDestinationPort(
                ipEntry.getSourceHostName(), ipEntry.getSourceIpAddress(), ipEntry.getDestinationHostName(),
                ipEntry.getDestinationIpAddress(), ipEntry.getDestinationPort()
        );
    }

    @Override
    //Returns true if the Ip Entry sent to us is missing information it needs
    public boolean infoMissing(IpEntry ipEntry) {
        return ipEntry.getSourceHostName() == null || ipEntry.getSourceIpAddress() == null ||
                ipEntry.getDestinationHostName() == null || ipEntry.getDestinationIpAddress() == null ||
                ipEntry.getDestinationPort() == null || ipEntry.getCreatedBy() == null;

    }

    @Override
    //Returns true if the applicationId sent with the Ip Entry doesn't belong to an Application
    public boolean applicationNotExists(String applicationId) {
        return !applicationRepository.existsByApplicationId(applicationId);
    }
}
