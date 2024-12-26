package com.example.ers.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ers.models.Reimbursement;
import com.example.ers.models.User;
import com.example.ers.services.ReimbursementService;
import com.example.ers.utils.AdminUtility;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reimbursements")
@RequiredArgsConstructor
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    //user story - create a new reimbursement ticket 
    @PostMapping
    public ResponseEntity<Reimbursement> createReimbursement(@RequestBody Reimbursement reimbursement, @RequestBody Long userId){
        Reimbursement createdReimbursement = reimbursementService.createReimbursement(reimbursement, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReimbursement);
    }
    
    //user story - allow user to see their reimbursement tickets
    @GetMapping("/{userId}")
    public ResponseEntity<?> seeMyReimbursements(@PathVariable Long userId, HttpSession session){

        User loggedInUser = (User) session.getAttribute("User");

        //check to make sure user is logged in 
        if(loggedInUser == null ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must log in to view your reimbursement tickets");
        }
        //check to make sure the user can only see their own reimbursement tickets
        if(!loggedInUser.getUserId().equals(userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only view your own reimbursement tickets");
        }

        List<Reimbursement> reimbursements = reimbursementService.seeMyReimbursements(userId);
        return ResponseEntity.ok(reimbursements);
    }

    //user story - allow user to see their pending tickets
    @GetMapping("/user/pending")
    public ResponseEntity<?> showUserPendingReimbursements(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must be logged in to view your pending reimbursement tickets.");
        }

        List<Reimbursement> pendingReimbursements = reimbursementService.seePendingReimbursements(user.getUserId());
        return ResponseEntity.ok(pendingReimbursements);
    }

    //additional user stories 
    //allow a user to update the details of a pending reimbursement ticket 
    @PutMapping("/{reimbursementId}")
    public ResponseEntity<?> updateReimbursement(@PathVariable Long reimbursementId, @RequestBody Reimbursement updatedReimbursement, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must be logged in to update your pending reimbursements");
        }

        try {
            Reimbursement existingReimbursement = reimbursementService.getReimbursementById(reimbursementId);

            if(!existingReimbursement.getUser().getUserId().equals(user.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only edit your own reimbursement tickets");
            }

            if(!existingReimbursement.getStatus().equalsIgnoreCase("pending")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only change a pending reimbursement ticket");
            }

            //validation check - only update the reimbursement ticket if amount is not empty and greater than 0
            if(updatedReimbursement.getAmount() != null && updatedReimbursement.getAmount() > 0){
                existingReimbursement.setAmount(updatedReimbursement.getAmount());
            }

            //validation check - only update the reimbursement ticket if the description is not empty 
            if(updatedReimbursement.getDescription() != null && updatedReimbursement.getDescription().isEmpty()){
                existingReimbursement.setDescription(updatedReimbursement.getDescription());

            }

            Reimbursement savedReimbursement = reimbursementService.updateReimbursement(existingReimbursement);
            return ResponseEntity.ok(savedReimbursement);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    //allow a user to delete a pending reimbursement ticket - must be pending and their own ticket 
    @DeleteMapping("/{reimbursementId}")
    public ResponseEntity<?> deletePendingReimbursement(@PathVariable Long reimbursementId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete reimbursement tickets");
        }

        try {
            Reimbursement existingReimbursement = reimbursementService.getReimbursementById(reimbursementId);

            if(!existingReimbursement.getUser().getUserId().equals(user.getUserId())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own pending tickets.");
            }

            if(!existingReimbursement.getStatus().equalsIgnoreCase("pending")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only delete a pending ticket");
            }

            reimbursementService.deleteReimbursement(reimbursementId);
            return ResponseEntity.ok("Reimbursement ticket deelted succesfully");
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    //manager user story - show an admin all pending reimbursements
    @GetMapping("/admin/pending") 
    public ResponseEntity<?> getAllPendingUserTickets(HttpSession session) {
        User user = (User) session.getAttribute("user");
          if (!AdminUtility.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Must be an admin to access this feature");
        }

        List<Reimbursement> pendingReimbursements = reimbursementService.showAllPendingReimbursements();
        if(pendingReimbursements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No pending reimbursement tickets found");
        }

        return ResponseEntity.ok(pendingReimbursements);
    }

    //manager user story - allow admin to resolve reimbursement
    @PutMapping("/{reimbursementId}/update-status")
    public ResponseEntity<?> resolveReimbursement(@PathVariable Long reimbursementId, @RequestParam String status, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to access this feature!");
        }

        if(!user.getRole().getRoleName().equalsIgnoreCase("admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You must be an admin to use this feature!");
        }

        try {
            Reimbursement resolvedReimbursement = reimbursementService.resolveReimbursement(reimbursementId, status);
            return ResponseEntity.ok(resolvedReimbursement);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    
    

}
