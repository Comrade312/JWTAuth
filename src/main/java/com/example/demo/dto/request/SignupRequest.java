package com.example.demo.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignupRequest {
    @NotBlank
    private String username;
 
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String surname;
}
