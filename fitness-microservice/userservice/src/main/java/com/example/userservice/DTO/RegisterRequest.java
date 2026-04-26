package com.example.userservice.DTO;

import lombok.Data;

@Data
// this is what is sent to us in get or post mappings
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
