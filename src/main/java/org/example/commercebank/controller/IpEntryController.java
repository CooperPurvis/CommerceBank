package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.domain.IpEntry;
import org.example.commercebank.service.IpEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ipEntry")
@AllArgsConstructor
public class IpEntryController {
    private IpEntryService ipEntryService;

//    @PostMapping
//    public ResponseEntity<IpEntry> createIpEntry(@RequestBody IpEntry ipEntry) {
//        return new ResponseEntity<>(ipEntryService.addIpEntry(ipEntry), HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<IpEntry> createIpEntry(@RequestBody Map<String, String> ipEntryInfo) {
        return new ResponseEntity<>(ipEntryService.addIpEntry(ipEntryInfo), HttpStatus.CREATED);
    }

//    @PutMapping
//    public ResponseEntity<IpEntry> updateIpEntry(@RequestBody Map<String, String> ipEntryInfo) {
//
//    }
}
