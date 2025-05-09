package org.example.portal.controller;

import org.example.portal.model.User;
import org.example.portal.repository.UserRepository;
import org.example.portal.service.FinanceService;
import org.example.portal.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        
        userRepository.save(user);
        
        return ResponseEntity.ok("Profile updated successfully");
    }

    @GetMapping("/graduation-eligibility")
    public ResponseEntity<?> checkGraduationEligibility() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        boolean hasOutstandingInvoices = financeService.hasOutstandingInvoices(user.getStudentId());
        boolean hasOverdueBooks = libraryService.hasOverdueBooks(user.getStudentId());
        
        if (hasOutstandingInvoices) {
            return ResponseEntity.ok("Not eligible for graduation due to outstanding invoices");
        }
        
        if (hasOverdueBooks) {
            return ResponseEntity.ok("Not eligible for graduation due to overdue library books");
        }
        
        return ResponseEntity.ok("Eligible for graduation");
    }
} 