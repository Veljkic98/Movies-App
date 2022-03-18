package com.auditserver.repository;

import java.util.Date;
import java.util.List;

import com.auditserver.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByUserEmail(String userEmail);
    
    List<Event> findByTimeBetween(Date startDate, Date endDate);
}
