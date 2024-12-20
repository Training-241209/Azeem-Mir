package com.example.ers.services;

import com.example.ers.models.Reimbursement;
import com.example.ers.models.User;
import com.example.ers.repositories.ReimbursementRepository;
import com.example.ers.repositories.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReimbursementService {
    private final ReimbursementRepository reimbursementRepository;
    private final UserRepository userRepository;

    // create a new reimbursement
    public Reimbursement createReimbursement(Reimbursement reimbursement, Long userId) {
        // return reimbursementRepository.save(reimbursement);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        reimbursement.setUser(user);
        reimbursement.setStatus("pending");
        return reimbursementRepository.save(reimbursement);
    }

    // allow a user to see all of their reimbursement tickets
    public List<Reimbursement> seeMyReimbursements(Long userId) {
        return reimbursementRepository.findByUserId(userId);
    }

    // show user their pending tickets
    public List<Reimbursement> seePendingReimbursements(Long userId) {
        return reimbursementRepository.findByStatus("pending");
    }

    // show pending reimbursements - for admins
    //need to add admin permission/restrict access to users
    public List<Reimbursement> showAllPendingReimbursements() {
        return reimbursementRepository.findByStatus("pending");
    }

    public Reimbursement resolveReimbursement(Long reimbursementId, String status) {
        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new RuntimeException("Reimbursement ticket does not exist"));
        if (!status.equals("approved") && !status.equals("denied")) {
            throw new IllegalArgumentException("The reimbursement ticket has to be either approved/denied");
        }

        reimbursement.setStatus(status);
        return reimbursementRepository.save(reimbursement);
    }

}
