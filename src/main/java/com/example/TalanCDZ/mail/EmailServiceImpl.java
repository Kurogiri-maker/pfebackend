package com.example.TalanCDZ.mail;

import com.example.TalanCDZ.domain.User;
import com.example.TalanCDZ.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    public void sendVerificationEmail(User user) throws MessagingException {

        try {
            InternetAddress emailAddress = new InternetAddress(user.getEmail());
            emailAddress.validate(); // Validates the email address
        } catch (AddressException ex) {
            // Handle invalid email address exception
            ex.printStackTrace();
            return;
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        String token = generateVerificationToken(user);

        String subject = "Email Verification";
        String text ="Please click on the link below to verify your email address:"+
                "http://localhost:8086/auth/verify?token=" + token;

        helper.setTo(user.getEmail());
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
        System.out.println(user);
        System.out.println(userRepository.findById(user.getId()).isPresent());
        if(userRepository.findById(user.getId()).isPresent()){
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;



    }


}
