package com.movieserver.domain.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateActorRequest {

    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;

    public CreateActorRequest() {
    }

    public CreateActorRequest(String firstName, String lastName) {
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
