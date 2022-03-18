package com.reportserver.service.actor;

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
import com.reportserver.model.Actor;
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
public class ActorService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private CSVWriter csvWriter;

    @Autowired
    private ActorCSVWriter actorCSVWriter;

    @Value("${com.levi9.movie-app.movie-service.ip-address}")
    private String movieServiceIpAddress;

    @Value("${com.levi9.movie-app.file.path}")
    private String filePath;

    public List<Actor> getAll() {

        HttpHeaders headers = tokenUtil.getAuthorizationHeader();
        
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://" + movieServiceIpAddress + ":8082/actor-management/actors";

        ResponseEntity<Actor[]> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                Actor[].class);


        List<Actor> actors = Arrays.asList(responseEntity.getBody());

        if (actors.isEmpty() || actors == null)
            throw new NoContentException();

        return actors;
    }

    /**
     * Method to create and download report file
     * with all actors, in CSV format, without any
     * third party library.
     * 
     * @throws IOException
     */
    public void createCSV(List<Actor> actors, String fileName) throws IOException {

        List<String[]> csvData = actorCSVWriter.createCsvDataSpecial(actors);

        fileName = fileName + "-" + csvWriter.getNameFromDateNow();

        File file = new File(filePath + fileName + ".csv");

        csvWriter.writeToCsvFile(csvData, file);
    }

    public void createExcel(List<Actor> actors, String fileName) throws IOException {

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

        for (int i = 0; i < actors.size(); i++) {
            data.put(
                (i + 2) + "",
                new Object[] { 
                    actors.get(i).getId().toString(), 
                    actors.get(i).getFirstName(), 
                    actors.get(i).getLastName()
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
            new File(filePath + fileName + ".xlsx")
        );
  
        workbook.write(out);

        out.close();

        workbook.close();
    }

    public void createPdf(List<Actor> actors, String fileName) throws DocumentException, IOException {

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
        for (Actor actor : actors) {
            PdfPCell pdfPCellId = new PdfPCell(new Phrase(actor.getId().toString()));
            PdfPCell pdfPCellFirstName = new PdfPCell(new Phrase(actor.getFirstName()));
            PdfPCell pdfPCellLastName = new PdfPCell(new Phrase(actor.getLastName()));

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
    
        System.out.println("Pdf created successfully.");
    }

}
