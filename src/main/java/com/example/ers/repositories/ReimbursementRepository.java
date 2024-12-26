package com.example.ers.repositories;

import com.example.ers.models.Reimbursement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {
    List<Reimbursement> findByStatus(String status);
    List<Reimbursement> findByUser_UserId(Long userId);
    List<Reimbursement> findByUser_UserIdAndStatus(Long userId, String status);
}
