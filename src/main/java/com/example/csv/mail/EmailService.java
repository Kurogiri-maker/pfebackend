package com.example.csv.mail;

import com.example.csv.domain.User;

import javax.mail.MessagingException;

public interface EmailService {

    public void sendVerificationEmail(String to ,String token) throws MessagingException;

    public String generateVerificationToken(User user);

    public Boolean verifyEmail(String token);


}
