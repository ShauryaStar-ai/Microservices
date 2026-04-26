package com.example.userservice.DTO;

import com.example.userservice.entity.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
// used on get user calls so the password is not exposed
@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
