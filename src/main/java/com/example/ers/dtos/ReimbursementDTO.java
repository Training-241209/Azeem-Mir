package com.example.ers.dtos;

import lombok.Data;

@Data
public class ReimbursementDTO {
    private Long reimbId;
    private String description;
    private Double amount;
    private String status;
    private Long userId;
    private String username;
}
