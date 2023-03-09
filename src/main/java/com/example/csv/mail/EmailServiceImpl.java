package com.example.csv.mail;

import com.example.csv.domain.User;
import com.example.csv.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendVerificationEmail(String to, String token) throws MessagingException {

        try {
            InternetAddress emailAddress = new InternetAddress(to);
            emailAddress.validate(); // Validates the email address
        } catch (AddressException ex) {
            // Handle invalid email address exception
            ex.printStackTrace();
            return;
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        String subject = "Email Verification";
        String text ="Please click on the link below to verify your email address:"+
                "http://localhost:8086/api/csv/verify?token=" + token;

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text,true);

        javaMailSender.send(message);
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = RandomString.make(30);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public Boolean verifyEmail(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();
        if(userRepository.findById(user.getId()).isPresent()){
            return true;
        }
        return false;



    }


}
