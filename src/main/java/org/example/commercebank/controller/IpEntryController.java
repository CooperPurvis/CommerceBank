package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.IpEntryExporter;
import org.example.commercebank.domain.IpEntry;
import org.example.commercebank.service.ApplicationService;
import org.example.commercebank.service.IpEntryService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class IpEntryController {
    private IpEntryService ipEntryService;
    private ApplicationService applicationService;

    @GetMapping("/api/ipEntry")
    public ResponseEntity<List<IpEntry>> getIpEntries() {
        return new ResponseEntity<>(ipEntryService.getIpEntries(), HttpStatus.OK);
    }

    @PostMapping("/api/ipEntry")
    public ResponseEntity<IpEntry> createIpEntry(@RequestBody Map<String, String> ipEntryInfo) {
        if(!ipEntryService.appExists(ipEntryInfo.get("applicationId")))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(ipEntryService.infoExists(ipEntryInfo))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(ipEntryService.addIpEntry(ipEntryInfo), HttpStatus.CREATED);
    }

    @PutMapping("/api/ipEntry")
    public ResponseEntity<IpEntry> updateIpEntry(@RequestBody Map<String, Object> ipEntryInfo) {

        if(!ipEntryService.ipEntryexists((Long)ipEntryInfo.get("ipEntryUid")))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(ipEntryService.updateIpEntry(ipEntryInfo), HttpStatus.OK);
    }

    @DeleteMapping("/api/ipEntry")
    public ResponseEntity<Void> deleteIpEntry(@RequestBody Map<String, Long> ipEntryInfo) {
        if(!ipEntryService.ipEntryexists(ipEntryInfo.get("ipEntryUid")))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        ipEntryService.deleteIpEntry(ipEntryInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/ipEntry/export")
    public ResponseEntity<byte[]> exportIpEntries() {
        List<IpEntry> ipEntryList = ipEntryService.getIpEntries();
        IpEntryExporter exporter = new IpEntryExporter(ipEntryList);
        ByteArrayOutputStream spreadsheet = exporter.export();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "Ip_Entry_List")//spreadsheet.getName())
                    .contentType(MediaType.parseMediaType("application/vnd.mx-excel"))
                    .contentLength(spreadsheet.size())
                    .body(spreadsheet.toByteArray());
    }

}
