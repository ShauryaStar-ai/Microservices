package com.example.userservice.Controller;


import com.example.userservice.DTO.RegisterRequest;
import com.example.userservice.Service.UserService;
import com.example.userservice.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired UserService userService;
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long userId){
        return ResponseEntity.status(200).body(userService.getUserProfile(userId));
    }
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }
    // used to make sure that the user is valid
    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable Long userId){
        boolean doesUserExists = userService.existsById(userId);
        return ResponseEntity.status(200).body(doesUserExists);
    }


}
