package com.example.demo.entity;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @NotBlank
    @NonNull
    private String username;

    @NotBlank
    @NonNull
    private String email;

    @NotBlank
    @NonNull
    private String password;

    private String surname;

}
