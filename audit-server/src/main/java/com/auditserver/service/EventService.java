package com.auditserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auditserver.model.Event;
import com.auditserver.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {

        List<Event> events = eventRepository.findAll();

        return events;
    }

    public List<Event> getEventsByUserEmail(String userEmail) {

        List<Event> events = eventRepository.findAllByUserEmail(userEmail);

        return events;
    }

    public List<Event> findAllByStartDateLessThanTimeAndEndDateGreaterThanTime(Date endDate, Date startDate) {

        List<Event> events = new ArrayList<>(); 
        events = eventRepository.findByTimeBetween(startDate, endDate);
    
        return events;
    }

    public Event save(Event event) {

        return eventRepository.save(event);
    }
    
}
