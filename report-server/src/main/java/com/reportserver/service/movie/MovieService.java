package com.reportserver.service.movie;

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
import com.reportserver.domain.dto.MovieReport;
import com.reportserver.domain.exception.exceptions.NoContentException;
import com.reportserver.domain.mapper.MovieMapper;
import com.reportserver.model.Actor;
import com.reportserver.model.Movie;
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
public class MovieService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private CSVWriter csvWriter;

    @Autowired
    private MovieCSVWriter movieCSVWriter;

    @Value("${com.levi9.movie-app.movie-service.ip-address}")
    private String movieServiceIpAddress;

    @Value("${com.levi9.movie-app.file.path}")
    private String filePath;

    public List<MovieReport> getAll() {

        HttpHeaders headers = tokenUtil.getAuthorizationHeader();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://" + movieServiceIpAddress + ":8082/movie-management/movies";
        
        ResponseEntity<Movie[]> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                Movie[].class);

        List<Movie> movies = Arrays.asList(responseEntity.getBody());

        if (movies.isEmpty() || movies == null)
            throw new NoContentException();

        List<MovieReport> reports = movieMapper.toReportList(movies);

        return reports;
    }

    public List<Actor> getAllActorsByMovieId(Long movieId) {

        HttpHeaders headers = tokenUtil.getAuthorizationHeader();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Actor[]> responseEntity = restTemplate.exchange(
                "http://" + movieServiceIpAddress + ":8082/movie-management/movies/" + movieId + "/actors",
                HttpMethod.GET,
                entity,
                Actor[].class);

        List<Actor> movies = Arrays.asList(responseEntity.getBody());

        if (movies.isEmpty() || movies == null)
            throw new NoContentException();
        
        return movies;
    }

    /**
     * Method to create and download report file
     * with all movies, in CSV format, without any
     * third party library.
     * 
     * @param movies
     * @param string
     * @throws IOException
     */
    public void createCSV(List<MovieReport> movies, String fileName) throws IOException {

        List<String[]> csvData = movieCSVWriter.createCsvDataSpecial(movies);

        fileName = fileName + "-" + csvWriter.getNameFromDateNow();

        File file = new File(filePath + fileName + ".csv");

        csvWriter.writeToCsvFile(csvData, file);
    }

    public void createExcel(List<MovieReport> movies, String fileName) throws IOException {

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
            new Object[] { "ID", "Name", "Genre", "Length", "Director Name", "Director Surename" }
        );

        for (int i = 0; i < movies.size(); i++) {
            data.put(
                (i + 2) + "",
                new Object[] { 
                    movies.get(i).getId().toString(), 
                    movies.get(i).getName(), 
                    movies.get(i).getGenre(), 
                    movies.get(i).getLength() + "", 
                    movies.get(i).getDirectorFirstname(), 
                    movies.get(i).getDirectorLastname() 
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

        fileName = fileName + " " + csvWriter.getNameFromDateNow();
  
        // writing the workbook into the excel file
        FileOutputStream out = new FileOutputStream(
            new File(filePath + fileName + ".xlsx")
        );
  
        workbook.write(out);

        out.close();

        workbook.close();
    }

    public void createPdf(List<MovieReport> movies, String fileName) throws DocumentException, IOException {

        Document document = new Document();

        fileName = fileName + "-" + csvWriter.getNameFromDateNow();
        
        OutputStream outputStream = 
            new FileOutputStream(new File(filePath + fileName + ".pdf"));
        
        // Create PDFWriter instance
        PdfWriter.getInstance(document, outputStream);
    
        //Open the document
        document.open();
    
        PdfPTable pdfPTable = new PdfPTable(5);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        String[] heads = new String[] { "ID", "Name", "Genre", "Length", "Director" };

        // Create and add cells to table
        for (String head : heads) {
            PdfPCell pdfPCell = new PdfPCell(new Phrase(head, headFont));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            pdfPTable.addCell(pdfPCell);
        }
    
        // Create and add actors
        for (MovieReport movie : movies) {
            PdfPCell pdfPCell1 = new PdfPCell(new Phrase(movie.getId().toString()));
            PdfPCell pdfPCell2 = new PdfPCell(new Phrase(movie.getName()));
            PdfPCell pdfPCell3 = new PdfPCell(new Phrase(movie.getGenre()));
            PdfPCell pdfPCell4 = new PdfPCell(new Phrase(movie.getLength() + ""));
            PdfPCell pdfPCell5 = new PdfPCell(new Phrase(movie.getDirectorFirstname() + " " + movie.getDirectorLastname()));

            pdfPCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell5.setHorizontalAlignment(Element.ALIGN_CENTER);

            pdfPTable.addCell(pdfPCell1);
            pdfPTable.addCell(pdfPCell2);
            pdfPTable.addCell(pdfPCell3);
            pdfPTable.addCell(pdfPCell4);
            pdfPTable.addCell(pdfPCell5);
        }

        // Add content to the document using Table objects.
        document.add(pdfPTable);
    
        // Close document and outputStream.
        document.close();
        
        outputStream.close();
    }

}
