package com.example.ers.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="reimbursements")
@Data
public class Reimbursement{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reimb_id")
    private Long reimbId;

    @Column(name="description")
    private String description;

    @Column(name="amount")
    private Double amount;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}