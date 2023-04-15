package com.example.TalanCDZ.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor


public class AuthenticationResponse {
    private String token;
    private String error;
}
