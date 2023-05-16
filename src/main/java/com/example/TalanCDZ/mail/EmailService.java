package com.example.TalanCDZ.mail;

import com.example.TalanCDZ.domain.User;

import javax.mail.MessagingException;
import java.net.UnknownHostException;

public interface EmailService {

    public void sendVerificationEmail(User user) throws MessagingException, UnknownHostException;

    public String generateVerificationToken(User user);

    public Boolean verifyEmail(String token);


}
