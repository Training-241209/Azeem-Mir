package com.example.ers.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Role")
@Data   
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;

    @Column(name="role_name", unique = true, nullable = false)
    private String roleName;

}
