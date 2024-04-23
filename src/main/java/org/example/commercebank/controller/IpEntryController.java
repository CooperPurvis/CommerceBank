package org.example.commercebank.controller;

import lombok.AllArgsConstructor;
import org.example.commercebank.IpEntryExporter;
import org.example.commercebank.domain.IpEntry;
import org.example.commercebank.service.IpEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class IpEntryController {
    private IpEntryService ipEntryService;

    //Return list of all Ip Entries
    @GetMapping("/api/ipEntry")
    @CrossOrigin
    public ResponseEntity<List<IpEntry>> getIpEntries(@RequestBody Map<String, List<Long>> json) {
        return new ResponseEntity<>(ipEntryService.getIpEntries(json.get("ipEntryUids")), HttpStatus.OK);
    }
    //Add an Ip Entry to the database
    @PostMapping("/api/ipEntry")
    @CrossOrigin
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
    @CrossOrigin
    public ResponseEntity<IpEntry> updateIpEntry(@RequestBody IpEntry ipEntry) {
        if(ipEntry.getIpEntryUid() == null || ipEntry.getApplication() == null)
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(ipEntryService.applicationNotExists(ipEntry.getApplication().getApplicationId()))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(ipEntryService.ipEntryUidNotExists(ipEntry.getIpEntryUid()))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        if(ipEntryService.infoMissing(ipEntry))
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(ipEntryService.infoExists(ipEntry))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(ipEntryService.updateIpEntry(ipEntry), HttpStatus.OK);
    }
    //Delete existing Ip Entry
    @DeleteMapping("/api/ipEntry")
    @CrossOrigin
    public ResponseEntity<Void> deleteIpEntry(@RequestBody IpEntry ipEntry) {
        if(ipEntry.getIpEntryUid() == null)
            return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
        if(ipEntryService.ipEntryUidNotExists(ipEntry.getIpEntryUid()))
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        ipEntryService.deleteIpEntry(ipEntry.getIpEntryUid());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Export Ip Entries to an Excel Spreadsheet
    @GetMapping("/api/ipEntry/export")
    @CrossOrigin
    public ResponseEntity<byte[]> exportIpEntries(@RequestBody Map<String, List<Long>> json) {
        //Get a list of Ip Entries to export to excel
        List<IpEntry> ipEntryList = ipEntryService.getIpEntries(json.get("ipEntryUids"));

        //Create an excelExporter and export the list to excel as a byte array
        IpEntryExporter excelExporter = new IpEntryExporter(ipEntryList);
        byte[] spreadsheet = excelExporter.export();


        //Return the spreadsheet as a byte array with headers that signal all needed information
        return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .contentLength(spreadsheet.length)
                    .body(spreadsheet);
    }

}
