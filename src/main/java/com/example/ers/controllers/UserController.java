package com.example.ers.controllers;
    
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ers.dtos.UserDTO;
import com.example.ers.models.User;
import com.example.ers.services.UserService;
import com.example.ers.utils.AdminUtility;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // user story - allow user to create an account
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if(existingUser.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already in use");

        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    // user story - allow user to login
   
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpSession session) {
        Optional<User> foundUser = userService.findByUsername(user.getUsername());
        UserService.validateLogin(user.getUsername(), user.getPassword());
       if (foundUser.isPresent()) {
        User existingUser = foundUser.get();
       
        if (userService.verifyPassword(user.getPassword(), existingUser.getPassword())) {
            session.setAttribute("user", existingUser);
            return ResponseEntity.ok("Login successful");
        }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid log in - please retry.");


    }
        

    //user story - users can logout

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully logged out.");
    }

    
    //search for users by username - admin
    
    @GetMapping("/search")
    public ResponseEntity<?>  findUserByUsername(@RequestParam String username, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!AdminUtility.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Must be an admin to access this feature");
        }
        Optional<User> foundUser = userService.findByUsername(username);
        if (foundUser.isPresent()) {
            return ResponseEntity.ok(convertToDTO(foundUser.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }



    //search for users by id - admin

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!AdminUtility.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Must be an admin to access this feature");
        }

        try {
            User foundUser = userService.findById(id);
            return ResponseEntity.ok(convertToDTO(foundUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    //get list of all users - admin function    
    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!AdminUtility.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Must be an admin to access this feature");
        }

        List<UserDTO> userDTOs = userService.findAllUsers().stream()
            .map(this::convertToDTO)
            .toList();

        return ResponseEntity.ok(userDTOs);
    }

    //delete a user by specifying id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(!AdminUtility.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Must be an admin to access this feature");
        }

        try { 
            userService.deleteUser(id);
            return ResponseEntity.ok("Sucessfully deleted user account");    
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //user data transfer object to prevent sensitive user information from being revealed
    public UserDTO convertToDTO(User user) {
    return new UserDTO(
        user.getUserId(),
        user.getFirstName(),
        user.getLastName(),
        user.getUsername(),
        user.getEmail(),
        user.getRole().getRoleName(),
        user.getReimbursements()
    );
}
    
}
