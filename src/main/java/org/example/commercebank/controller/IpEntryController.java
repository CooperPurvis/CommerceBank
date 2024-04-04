package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.IpEntryExporter;
import org.example.commercebank.domain.IpEntry;
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
@RequestMapping("/api/ipEntry")
@AllArgsConstructor
public class IpEntryController {
    private IpEntryService ipEntryService;


    @GetMapping
    public ResponseEntity<List<IpEntry>> getIpEntries() {
        return new ResponseEntity<>(ipEntryService.getIpEntries(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IpEntry> createIpEntry(@RequestBody Map<String, String> ipEntryInfo) {
        return new ResponseEntity<>(ipEntryService.addIpEntry(ipEntryInfo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<IpEntry> updateIpEntry(@RequestBody Map<String, String> ipEntryInfo) {
        return new ResponseEntity<>(ipEntryService.updateIpEntry(ipEntryInfo), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIpEntry(@RequestBody Map<String, Long> ipEntryInfo) {
        ipEntryService.deleteIpEntry(ipEntryInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/export")
    @GetMapping()
    public ResponseEntity<byte[]> exportIpEntries() {
        List<IpEntry> ipEntryList = ipEntryService.getIpEntries();
        IpEntryExporter exporter = new IpEntryExporter(ipEntryList);
        ByteArrayOutputStream spreadsheet = exporter.export();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + spreadsheet.getName())
                    .contentType(MediaType.parseMediaType("application/vnd.mx-excel"))
                    .contentLength(spreadsheet.size())
                    .body(spreadsheet.toByteArray());
    }

}
