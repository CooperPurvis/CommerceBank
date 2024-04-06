package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.IpEntryExporter;
import org.example.commercebank.domain.IpEntry;
import org.example.commercebank.service.IpEntryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class IpEntryController {
    private IpEntryService ipEntryService;

    //Return list of all Ip Entries
    @GetMapping("/api/ipEntry")
    public ResponseEntity<List<IpEntry>> getIpEntries() {
        return new ResponseEntity<>(ipEntryService.getIpEntries(), HttpStatus.OK);
    }
    //Add an Ip Entry to the database
    @PostMapping("/api/ipEntry")
    public ResponseEntity<IpEntry> createIpEntry(@RequestBody IpEntry ipEntry) {
        if(ipEntryService.applicationNotExists(ipEntry.getApplication().getApplicationId()))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(ipEntryService.infoExists(ipEntry))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(ipEntryService.infoMissing(ipEntry))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(ipEntryService.addIpEntry(ipEntry), HttpStatus.CREATED);
    }
    //Update existing Ip Entry in the database
    @PutMapping("/api/ipEntry")
    public ResponseEntity<IpEntry> updateIpEntry(@RequestBody IpEntry ipEntry) {
        if(ipEntryService.applicationNotExists(ipEntry.getApplication().getApplicationId()))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(ipEntryService.ipEntryUidNotExists(ipEntry.getIpEntryUid()))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(ipEntryService.infoExists(ipEntry))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(ipEntryService.infoMissing(ipEntry))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        return new ResponseEntity<>(ipEntryService.updateIpEntry(ipEntry), HttpStatus.OK);
    }
    //Delete existing Ip Entry
    @DeleteMapping("/api/ipEntry")
    public ResponseEntity<Void> deleteIpEntry(@RequestBody IpEntry ipEntry) {
        if(ipEntryService.ipEntryUidNotExists(ipEntry.getIpEntryUid()))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        ipEntryService.deleteIpEntry(ipEntry.getIpEntryUid());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Export Ip Entries to an Excel Spreadsheet
    @GetMapping("/api/ipEntry/export")
    public ResponseEntity<byte[]> exportIpEntries() {
        List<IpEntry> ipEntryList = ipEntryService.getIpEntries();
        IpEntryExporter exporter = new IpEntryExporter(ipEntryList);

        byte[] spreadsheet = exporter.export();
        /* Add a way to get date as a string in the fileName */
        String fileName = "Ip_Entry_List_2024_04_05_10:27.xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .contentLength(spreadsheet.length)
                    .body(spreadsheet);
    }

}
