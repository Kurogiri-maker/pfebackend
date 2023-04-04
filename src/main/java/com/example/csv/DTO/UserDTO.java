package com.example.csv.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private boolean enabled;
}
