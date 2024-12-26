package com.example.ers.models;

import jakarta.persistence.*;
import lombok.Data;

//role entity class used for account role types, speficially "employee" and "admin"
@Entity
@Table(name="roles")
@Data   
public class Role{
    public static final String ADMIN = "admin";
    public static final String EMPLOYEE = "employee"; 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;

    @Column(name="role_name", unique = true, nullable = false)
    private String roleName;

}
