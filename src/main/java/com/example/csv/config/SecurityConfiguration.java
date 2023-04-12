package com.example.csv.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/kafka/**").permitAll()
                .antMatchers("/swagger-ui/**", "/actuator/**").permitAll() // allow access to Swagger and Actuator
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
               // .antMatchers("/api/user/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()

//                .anyRequest().permitAll(); //For no security uncomment this line and comment all the lines below

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
