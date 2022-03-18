package com.authserver.domain.dto.request;

public class CreateUserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String rePassword;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String firstName, String lastName, String email, String password, String rePassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRePassword() {
        return this.rePassword;
    }

}
