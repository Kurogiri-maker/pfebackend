package com.example.TalanCDZ.config;

import com.example.TalanCDZ.domain.Role;
import com.example.TalanCDZ.domain.User;
import com.example.TalanCDZ.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@AllArgsConstructor
@Component
public class AdminInitializer {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("admin@admin.com").isEmpty()){
            userRepository.save(User.builder()
                    .email("admin@admin.com")
                    .firstName("admin")
                    .lastName("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.valueOf("ADMIN"))
                    .enabled(true)
                    .build());
        }
    }

}
