package com.example.ers.services;

import com.example.ers.models.Reimbursement;
import com.example.ers.models.User;
import com.example.ers.repositories.ReimbursementRepository;
import com.example.ers.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReimbursementService {
    private final ReimbursementRepository reimbursementRepository;
    private final UserRepository userRepository;

    // create a new reimbursement
    public Reimbursement createReimbursement(Reimbursement reimbursement, Long userId) {
        validateReimbursement(reimbursement);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        reimbursement.setUser(user);
        reimbursement.setStatus("pending");
        return reimbursementRepository.save(reimbursement);
    }

    // allow a user to see all of their reimbursement tickets
    public List<Reimbursement> seeMyReimbursements(Long userId) {
        return reimbursementRepository.findByUser_UserId(userId);
    }

    // show user their pending tickets
    public List<Reimbursement> seePendingReimbursements(Long userId) {
        return reimbursementRepository.findByUser_UserIdAndStatus(userId, "pending");
    }

    //find a reimbursement ticket by id - used for editing & deleting reimbursement ticket
    public Reimbursement getReimbursementById(Long reimbursementId) {
        Optional<Reimbursement> optionalReimbursement = reimbursementRepository.findById(reimbursementId);
        if(optionalReimbursement.isPresent()){
            return optionalReimbursement.get();
        } else{
            throw new RuntimeException("No reimbursement tickets found with ticket ID: " + reimbursementId);
        }

    }
    
    public Reimbursement updateReimbursement(Reimbursement reimbursement) {
        return reimbursementRepository.save(reimbursement);
    }
    
    //user story - allow user to delete a reimbursement ticket

    public void deleteReimbursement(Long reimbursementId){
        Optional<Reimbursement> optionalReimbursement = reimbursementRepository.findById(reimbursementId);

        if(optionalReimbursement.isPresent()){
            reimbursementRepository.delete(optionalReimbursement.get());
        } else{
            throw new RuntimeException("Cannot delete reimbursement ticket with ID: " + reimbursementId + ". Ticket does not exist");
        }
        }

    // show pending reimbursements - for admins
    //need to add admin permission/restrict access to users
    public List<Reimbursement> showAllPendingReimbursements() {
        return reimbursementRepository.findByStatus("pending");
    }

    public Reimbursement resolveReimbursement(Long reimbursementId, String status) {       
        if (!status.equalsIgnoreCase("approved") && !status.equalsIgnoreCase("denied")) {
            throw new IllegalArgumentException("Invalid ticket status. Must be approved or denied");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId)
                .orElseThrow(() -> new RuntimeException("Reimbursement ticket does not exist"));

        reimbursement.setStatus(status.toLowerCase());
        return reimbursementRepository.save(reimbursement);
    }

    private void validateReimbursement(Reimbursement reimbursement) {
        if(reimbursement.getAmount() == null || reimbursement.getAmount() <= 0) {
            throw new IllegalArgumentException("Reimbursement amount must be greater than 0");
        }

        if(reimbursement.getDescription() == null) {
            throw new IllegalArgumentException("Reimbursement description cannot be empty");
        }
    }
}
