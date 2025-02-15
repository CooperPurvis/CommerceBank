package org.example.commercebank;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.example.commercebank.domain.IpEntry;

import java.io.ByteArrayOutputStream;
import java.util.List;

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
        CellStyle headerStyle = getHeaderStyle();
        XSSFRow headerRow = worksheet.createRow(0);
        for(int i = 0; i < headerRowArray.length; i++) {
            XSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerRowArray[i]);
            headerCell.setCellStyle(headerStyle);
        }
    }

    private CellStyle getHeaderStyle() {
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setFontName("Comic Sans MS");
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    private void addIpEntries() {
        CellStyle defaultStyle = getDefaultStyle();
        for(int i = 0; i < ipEntryList.size(); i++) {
            XSSFRow row = worksheet.createRow(i + 1);
            String[] ipEntryInfo = ipEntryList.get(i).toExcelRow();
            for(int j = 0; j < ipEntryInfo.length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(ipEntryInfo[j]);
                cell.setCellStyle(defaultStyle);
            }
        }
    }

    private CellStyle getDefaultStyle() {
        CellStyle defaultStyle = workbook.createCellStyle();
        XSSFFont defaultFont = workbook.createFont();
        defaultFont.setFontHeightInPoints((short) 11);
        defaultFont.setFontName("Calibri");
        defaultStyle.setFont(defaultFont);
        return defaultStyle;
    }

    public byte[] export() {
        openWorkbook();
        createHeaderRow();
        addIpEntries();

        for(int i = 10; i >= 0; i--)
            worksheet.autoSizeColumn(i);

        ByteArrayOutputStream fileWriter = new ByteArrayOutputStream();
        try {
            workbook.write(fileWriter);
            workbook.close();
        }
        catch(Exception e) {
            System.out.println("Problem!!\n\n");
        }
        return fileWriter.toByteArray();
    }
}
