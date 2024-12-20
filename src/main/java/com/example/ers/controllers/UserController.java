package com.example.ers.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ers.models.User;
import com.example.ers.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // user story - allow user to create an account
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(User user) {
        if(userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already in use");
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    // user story - allow user to login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(User user, HttpSession session) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (userService.verifyPassword(user.getPassword(), foundUser.getPassword())) {
            session.setAttribute("user", foundUser);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid log in - please retry.");
        }
    }

    //user story - users can logout

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Successfully logged out.");
    }

    //admin features below:
    //add admin restrictions 
    
    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername(String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("userById")
    public ResponseEntity<User> getUserById(Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

}
