package com.reportserver.service.movie;

import java.util.ArrayList;
import java.util.List;

import com.reportserver.domain.dto.MovieReport;

import org.springframework.stereotype.Service;

@Service
public class MovieCSVWriter {

    /**
     * Method to create list of arrays of type string. 
     * Array represents properties of movies.
     * 
     * @param movies is list of movies
     * @return list of arrays of type string
     */
    public List<String[]> createCsvDataSpecial(List<MovieReport> movies) {

        String[] header = {"Id", "Name", "Genre", "Length", "Director name", "Director surename"};

        List<String[]> list = new ArrayList<>(){{
            add(header);
            movies.forEach(a -> {
                String[] record = { a.getId() + "", a.getName(), a.getGenre(), a.getLength() + "",
                            a.getDirectorFirstname(), a.getDirectorLastname() };
                add(record);
            });
        }};

        return list;
    }
    
}
