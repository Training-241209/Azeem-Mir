package com.example.ers.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ers.models.Reimbursement;
import com.example.ers.services.ReimbursementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reimbursements")
@RequiredArgsConstructor
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    //user story - create a new reimbursement ticket 
    @PostMapping
    public ResponseEntity<Reimbursement> createReimbursement(Reimbursement reimbursement, Long userId){
        Reimbursement createdReimbursement = reimbursementService.createReimbursement(reimbursement, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReimbursement);
    }
    
    //user story - allow user to see their reimbursement tickets
    @GetMapping("/{userId}")
    public ResponseEntity<List<Reimbursement>> seeMyReimbursements(Long userId){
        List<Reimbursement> reimbursements = reimbursementService.seeMyReimbursements(userId);
        return ResponseEntity.ok(reimbursements);
    }

    //user story - allow user to see their pending tickets
    @GetMapping("/pending")
    public ResponseEntity<List<Reimbursement>> showAllPendingReimbursements() {
        List<Reimbursement> pendingReimbursements = reimbursementService.showAllPendingReimbursements();
        return ResponseEntity.ok(pendingReimbursements);
    }

    //user story - allow admin to resolve reimbursement
    @PutMapping("/{reimbursementId}")
    public ResponseEntity<Reimbursement> resolveReimbursement(Long reimbursementId, String status) {
        Reimbursement resolvedReimbursement = reimbursementService.resolveReimbursement(reimbursementId, status);
        return ResponseEntity.ok(resolvedReimbursement);
    }
    

}
