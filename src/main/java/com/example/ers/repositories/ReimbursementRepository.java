package com.example.ers.repositories;

import com.example.ers.models.Reimbursement;
//import com.example.ers.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {
    List<Reimbursement> findByStatus(String status);
    List<Reimbursement> findByUserId(Long userId);

}
