package com.example.TalanCDZ.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class EmailController {

    // @Autowired
    // private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /*
     * @PostMapping("/send")
     * public ResponseEntity<?> sendEmail(@RequestParam("to") String to) throws
     * MessagingException {
     * User user = new
     * User(null,"iheb","cherif","iheb.cherif99@gmail.com","testtest", Role.USER);
     * userRepository.save(user);
     * String token = emailService.generateVerificationToken(user);
     * emailService.sendVerificationEmail(to,token);
     * return new ResponseEntity<>(HttpStatus.OK);
     * }
     */

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token, HttpServletResponse response)
            throws IOException {
        Boolean val = emailService.verifyEmail(token);
        if (val) {
            String redirectUrl = "http://localhost:4200/auth/login"; // REDIRECT TO LOGIN PAGE
            response.sendRedirect(redirectUrl);
            return null;
        }
        return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);

    }
}
