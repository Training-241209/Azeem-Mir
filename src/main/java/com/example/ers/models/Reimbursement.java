package com.example.ers.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

//reimbursement entity class, corresponds to reimbursement table for ticket data
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

    //a user can have many tickets
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonBackReference
    private User user;

}