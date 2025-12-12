package com.ty.poll.controller;

import com.ty.poll.model.User;
import com.ty.poll.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    
    private final UserService userService;
   public UserController(UserService userService) {
        this.userService = userService;
    }
   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody User loginData) {
       User user = userService.loginUser(loginData.getUsername(), loginData.getPassword());
       
       if (user != null) {
           // Just say "OK"
           return ResponseEntity.ok("Login successful");
       }
       return ResponseEntity.status(401).body("Invalid credentials");
   }
    
    }
