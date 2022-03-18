package com.authserver.domain.dto.request; 

import javax.validation.constraints.NotBlank;

public class UserLoginRequest {

    @NotBlank(message = "Username cannot be null.")
    private String username;

    @NotBlank(message = "Password cannot be null.")
    private String password;

    public UserLoginRequest() {
    }

    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
