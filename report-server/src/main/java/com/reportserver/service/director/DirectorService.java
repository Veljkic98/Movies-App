package com.reportserver.service.director;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.reportserver.domain.exception.exceptions.NoContentException;
import com.reportserver.model.Director;
import com.reportserver.security.JwtTokenUtil;
import com.reportserver.service.writers.CSVWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DirectorService {
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private CSVWriter csvWriter;

    @Autowired
    private DirectorCSVWriter directorCSVWriter;

    @Value("${com.levi9.movie-app.movie-service.ip-address}")
    private String movieServiceIpAddress;

    @Value("${com.levi9.movie-app.file.path}")
    private String filePath;

    public List<Director> getAll() {
        
        HttpHeaders headers = tokenUtil.getAuthorizationHeader();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://" + movieServiceIpAddress + ":8082/director-management/directors";

        ResponseEntity<Director[]> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                Director[].class);

        List<Director> directors = Arrays.asList(responseEntity.getBody());

        if (directors.isEmpty() || directors == null)
            throw new NoContentException();

        return directors;
    }

    /**
     * Method to create and download report file
     * with all directors, in CSV format, without any
     * third party library.
     * 
     * @throws IOException
     */
    public void createCSV(List<Director> directors, String fileName) throws IOException {

        List<String[]> csvData = directorCSVWriter.createCsvDataSpecial(directors);

        fileName = fileName + "-" + csvWriter.getNameFromDateNow();

        File file = new File("/app/" + fileName + ".csv");

        csvWriter.writeToCsvFile(csvData, file);
    }

    public void createExcel(List<Director> directors, String fileName) throws IOException {

        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();
  
        // spreadsheet object
        XSSFSheet spreadsheet
            = workbook.createSheet(" Actor Report Data ");
  
        // creating a row object
        XSSFRow row;
  
        // This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put(
            "1",
            new Object[] { "ID", "First name", "Last name" }
        );

        for (int i = 0; i < directors.size(); i++) {
            data.put(
                (i + 2) + "",
                new Object[] { 
                    directors.get(i).getId().toString(), 
                    directors.get(i).getFirstName(), 
                    directors.get(i).getLastName()
                }
            );
        }
  
        Set<String> keyid = data.keySet();
  
        int rowid = 0;
  
        // writing the data into the sheets
        for (String key : keyid) {
  
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = data.get(key);
            
            int cellid = 0;
  
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        fileName = fileName + "-" + csvWriter.getNameFromDateNow();
  
        // writing the workbook into the excel file
        FileOutputStream out = new FileOutputStream(
            new File("/app/" + fileName + ".xlsx")
        );
  
        workbook.write(out);

        out.close();

        workbook.close();
    }

    public void createPdf(List<Director> directors, String fileName) throws IOException ,DocumentException {

        Document document = new Document();

        fileName = fileName + "-" + csvWriter.getNameFromDateNow();
        
        OutputStream outputStream = 
            new FileOutputStream(new File(filePath + fileName + ".pdf"));
        
        // Create PDFWriter instance
        PdfWriter.getInstance(document, outputStream);
    
        //Open the document
        document.open();
    
        PdfPTable pdfPTable = new PdfPTable(3);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        String[] heads = new String[] { "ID", "First name", "Last name" };

        // Create and add cells to table
        for (String head : heads) {
            PdfPCell pdfPCell = new PdfPCell(new Phrase(head, headFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            pdfPTable.addCell(pdfPCell);
        }
    
        // Create and add actors
        for (Director director : directors) {
            PdfPCell pdfPCellId = new PdfPCell(new Phrase(director.getId().toString()));
            PdfPCell pdfPCellFirstName = new PdfPCell(new Phrase(director.getFirstName()));
            PdfPCell pdfPCellLastName = new PdfPCell(new Phrase(director.getLastName()));

            pdfPCellId.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCellFirstName.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCellLastName.setHorizontalAlignment(Element.ALIGN_CENTER);

            pdfPTable.addCell(pdfPCellId);
            pdfPTable.addCell(pdfPCellFirstName);
            pdfPTable.addCell(pdfPCellLastName);
        }

        // Add content to the document using Table objects.
        document.add(pdfPTable);
    
        // Close document and outputStream.
        document.close();
        outputStream.close();
    }

}
