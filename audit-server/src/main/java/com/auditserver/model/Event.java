package com.auditserver.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "time", nullable = false)
    private Date time;

    public Event() {
    }

    public Event(String userEmail, String message, Date time) {
        // this.id = id;
        this.userEmail = userEmail;
        this.message = message;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return "{" +
            " id='" + getId() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", message='" + getMessage() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
    
}
