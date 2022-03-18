package com.authserver.model;

import java.util.Date;

public class Event {

    private Long id;
    
    private String userEmail;
    
    private String message;

    private Date time;

    public Event() {
    }

    public Event(String userEmail, String message, Date time) {
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
