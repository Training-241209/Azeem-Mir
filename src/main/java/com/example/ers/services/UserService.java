package com.example.ers.services;

import com.example.ers.models.Role;
import com.example.ers.models.User;
import com.example.ers.repositories.RoleRepository;
import com.example.ers.repositories.UserRepository;
import java.util.List;
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
       
    
        Role employeeRole = roleRepository.findByName("employee")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRole(employeeRole);
        
      
        //salt & hash pw using BCrypt then set  
        //default log_rounds is 10 with BCrypt, chose 12 to change it up
        //trade off is security vs performance 
       
        String hashedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPw);

        //create user session
        return userRepository.save(user);
    }

    //check raw pw with hashed pw for verification upon user login
    public Boolean verifyPassword(String rawPw, String hashedPw) {
        return BCrypt.checkpw(rawPw, hashedPw);
    }


    //methods for admins below: 
    //need to add restrictions for non admin users

    //search for users by username
    public User findByUsername(String username){
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Username does not exist"));

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
