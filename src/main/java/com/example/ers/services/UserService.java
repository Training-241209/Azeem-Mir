package com.example.ers.services;

import com.example.ers.models.Role;
import com.example.ers.models.User;
import com.example.ers.repositories.RoleRepository;
import com.example.ers.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    //user story - create an account 
    public User registerUser(User user){
        //validate for duplicate username/email entries
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username is already in use");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email is already in use");
        }

        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
      
        //salt & hash pw using BCrypt then set  
        //default log_rounds is 10 with BCrypt, chose 12 to change it up
        //trade off is security vs performance 
        String hashedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPw);
        
        Role employeeRole = roleRepository.findByRoleName("employee")
            .orElseThrow(() -> new RuntimeException("Role 'employee' does not exist in database"));
        user.setRole(employeeRole);

        //create user session
        return userRepository.save(user);
    }

    private void validatePassword(String password) {
        if(password == null || password.length() < 8){
            throw new IllegalArgumentException("Password must be 8 characters minimum.");
        }
        if(!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain an uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number.");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
        }
    }

    private void validateUsername(String username){
        if (username == null || username.length() < 5 || username.length() > 20) {
            throw new IllegalArgumentException("Username must be between 5 and 20 characters.");
        }
        if (!username.matches("^[a-zA-Z0-9_.-]*$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, dots, dashes, and underscores.");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken.");
        }
    }
    private  void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use.");
        }
    }

    public static void validateLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username field cannot be blank");
        }
        if (password == null || password.trim().isEmpty()){
            throw new IllegalArgumentException("Password field cannot be blank");
        }
    }
    //check raw pw with hashed pw for verification upon user login
    public Boolean verifyPassword(String rawPw, String hashedPw) {
        return BCrypt.checkpw(rawPw, hashedPw);
    }


    //methods for admins below: 
    //need to add restrictions for non admin users

    //search for users by username
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    
    //search for users by id
    public User findById(Long id){
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User id does not exist"));
    }

    //user story - see all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //user story - delete a user 
    public void deleteUser(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }



    //add optional functionality later






}
