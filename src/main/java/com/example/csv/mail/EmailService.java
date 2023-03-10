package com.example.csv.mail;

import com.example.csv.domain.User;

import javax.mail.MessagingException;

public interface EmailService {

    public void sendVerificationEmail(User user) throws MessagingException;

    public String generateVerificationToken(User user);

    public Boolean verifyEmail(String token);


}
