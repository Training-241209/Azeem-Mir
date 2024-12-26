package com.example.ers.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

//user entity class, corresponds to user table for user information
@Entity
@Table(name="users")
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(unique=true, nullable=false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName="role_id", nullable=false)
    private Role role;

    //a user can have many ticketss
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reimbursement> reimbursements;

   @Override
   public String toString() {
       return "User{" +
               "userId=" + userId +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", role=" + (role != null ? role.getRoleName() : "null") +
               '}';
   }
   
}