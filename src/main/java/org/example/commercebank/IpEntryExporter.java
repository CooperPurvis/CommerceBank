package org.example.commercebank;

import org.apache.poi.xssf.usermodel.*;
import org.example.commercebank.domain.IpEntry;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class IpEntryExporter {
    private static final String[] headerRowArray = {"Application Id", "Source Hostname", "Source IP Address", "Destination Hostname",
            "Destination IP Address", "Destination Port", "Status", "Created At", "Created By", "Modified At", "Modified By"};
    private final XSSFWorkbook workbook;
    private XSSFSheet worksheet;
    private final List<IpEntry> ipEntryList;

    public IpEntryExporter(List<IpEntry> ipEntryList) {
        XSSFWorkbookFactory factory = new XSSFWorkbookFactory();
        workbook = factory.create();
        this.ipEntryList = ipEntryList;
    }

    private void openWorkbook() {
        worksheet = workbook.createSheet("Ip Entry List");
    }

    private void createHeaderRow() {
        XSSFRow headerRow = worksheet.createRow(0);
        for(int i = 0; i < headerRowArray.length; i++) {
            XSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerRowArray[i]);
        }
    }

    private void addIpEntries() {
        for(int i = 0; i < ipEntryList.size(); i++) {
            XSSFRow row = worksheet.createRow(i + 1);
            String[] ipEntryInfo = ipEntryList.get(i).toExcelRow();
            for(int j = 0; j < ipEntryInfo.length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(ipEntryInfo[j]);
            }
        }
    }

//    private File writeToFile() {
//        Date date = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago")).getTime();
//        //String dateString = String.format("%s_%s_%s_%s_%s_%s", date.getTime())
//        String fileName = "Ip_Entry_List";//_" + dateString;
//        //System.out.println("Filename: " + fileName);
//        //File file = new File(fileName + ".xlsx");

//        ByteArrayOutputStream fileWriter = new ByteArrayOutputStream();
//        try {
//            FileOutputStream fileWriter = new FileOutputStream(file);
//            workbook.write(fileWriter);
//            fileWriter.close();
//            workbook.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error Opening File Writer!\n");
//            return null;
//        }
//
//        return file;
//    }

    public ByteArrayOutputStream export() {
        openWorkbook();
        createHeaderRow();
        addIpEntries();
        ByteArrayOutputStream fileWriter = new ByteArrayOutputStream();
        try {
            workbook.write(fileWriter);
            workbook.close();
        }
        catch(Exception e) {
            System.out.println("Problem!!\n\n");
        }
        return fileWriter;//writeToFile();
    }
}
