package com.movieserver.domain.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateDirectorRequest {

    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;

    public CreateDirectorRequest() {
    }

    public CreateDirectorRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String toString() {
        return "{" +
            " firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
