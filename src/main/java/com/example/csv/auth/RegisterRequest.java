package com.example.csv.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
