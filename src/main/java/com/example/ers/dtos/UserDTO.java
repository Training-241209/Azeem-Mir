package com.example.ers.dtos;

import java.util.List;

import com.example.ers.models.Reimbursement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
    private List<Reimbursement> reimbursements;

}
