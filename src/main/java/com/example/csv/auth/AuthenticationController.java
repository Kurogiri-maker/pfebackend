package com.example.csv.auth;


import com.example.csv.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterRequest> register(
            @RequestBody RegisterRequest request) throws MessagingException {
        try {
            service.register(request);

            return ResponseEntity.ok(request);
        }
        catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(request);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().error(e.getMessage()).build());
        }
        catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().error(e.getMessage()).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().error(e.getMessage()).build());
        }

    };
}
