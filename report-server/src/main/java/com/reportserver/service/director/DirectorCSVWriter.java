package com.reportserver.service.director;

import java.util.ArrayList;
import java.util.List;

import com.reportserver.model.Director;

import org.springframework.stereotype.Service;

@Service
public class DirectorCSVWriter {

    /**
     * Method to create list of arrays of type string. 
     * Array represents properties of directors.
     * 
     * @param directors is list of directors
     * @return list of arrays of type string
     */
    public List<String[]> createCsvDataSpecial(List<Director> directors) {

        String[] header = {"Id", "First Name", "Last Name"};

        List<String[]> list = new ArrayList<>(){{
            add(header);
            directors.forEach(a -> {
                String[] record = { a.getId() + "", a.getFirstName(), a.getLastName() };
                add(record);
            });
        }};

        return list;
    }
    
}
