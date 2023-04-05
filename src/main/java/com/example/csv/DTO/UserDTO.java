package com.example.csv.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private boolean enabled;
}
