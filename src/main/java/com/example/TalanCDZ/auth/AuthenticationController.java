package com.example.TalanCDZ.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.net.UnknownHostException;

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
            System.out.println("Request new user(controller)  : " + request);
            service.register(request);

            return ResponseEntity.ok(request);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(request);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().error(e.getMessage()).build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().error(e.getMessage()).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder().error(e.getMessage()).build());
        }

    };
}
