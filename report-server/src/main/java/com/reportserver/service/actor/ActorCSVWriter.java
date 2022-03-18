package com.reportserver.service.actor;

import java.util.ArrayList;
import java.util.List;

import com.reportserver.model.Actor;

import org.springframework.stereotype.Service;

@Service
public class ActorCSVWriter {

    /**
     * Method to create list of arrays of type string. 
     * Array represents properties of actors.
     * 
     * @param actors is list of actors
     * @return list of arrays of type string
     */
    public List<String[]> createCsvDataSpecial(List<Actor> actors) {

        String[] header = {"Id", "First Name", "Last Name"};

        List<String[]> list = new ArrayList<>(){{
            add(header);
            actors.forEach(a -> {
                String[] record = { a.getId() + "", a.getFirstName(), a.getLastName() };
                add(record);
            });
        }};

        return list;
    }
    
}
