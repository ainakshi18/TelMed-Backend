package TeleMed.TeleMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import TeleMed.TeleMed.Models.MedicalStore;
import TeleMed.TeleMed.Models.User;
import TeleMed.TeleMed.Models.UserRole;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.repository.UserRepository;
import TeleMed.TeleMed.service.MedicalStoreService;

import java.util.List;

@RestController
@RequestMapping("/api/medicalstore")
public class MedicalStoreController {

    @Autowired
    private MedicalStoreService medicalStoreService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Helper method to check if the user has ROLE_PHARMACIST
    private boolean hasPharmacistRole(User user) {
        return user.getUserRole() == UserRole.ROLE_PHARMACIST;
    }

    // Helper method to extract user from token
    private User getUserFromToken(String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        String userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Create a new medical store
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> createMedicalStore(@RequestBody MedicalStore medicalStore, @RequestHeader("Authorization") String token) {
        try {
            User user = getUserFromToken(token);

            // Populate medical store with user details
            medicalStore.setName(user.getName());
            medicalStore.setId(user.getId());
            medicalStore.setEmail(user.getEmail());

            // Save the medical store
            MedicalStore newStore = medicalStoreService.createMedicalStore(medicalStore);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStore);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating medical store: " + e.getMessage());
        }
    }

    // Get all medical stores
    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_PHARMACIST') or hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> getAllMedicalStores() {
        try {
            List<MedicalStore> medicalStores = medicalStoreService.getAllMedicalStores();
            return ResponseEntity.ok(medicalStores);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving medical stores: " + e.getMessage());
        }
    }

    // Get a single medical store by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PHARMACIST') or hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> getMedicalStoreById(@PathVariable String id) {
        try {
            MedicalStore medicalStore = medicalStoreService.getMedicalStoreById(id);
            if (medicalStore != null) {
                return ResponseEntity.ok(medicalStore);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical store not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error retrieving medical store: " + e.getMessage());
        }
    }

    // Update a medical store by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> updateMedicalStore(@PathVariable String id, @RequestBody MedicalStore medicalStore) {
        try {
            MedicalStore updatedStore = medicalStoreService.updateMedicalStore(id, medicalStore);
            if (updatedStore != null) {
                return ResponseEntity.ok(updatedStore);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical store not found for update");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating medical store: " + e.getMessage());
        }
    }

    // Delete a medical store by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> deleteMedicalStore(@PathVariable String id) {
        try {
            boolean deleted = medicalStoreService.deleteMedicalStore(id);
            if (deleted) {
                return ResponseEntity.ok("Medical store deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical store not found for deletion");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting medical store: " + e.getMessage());
        }
    }
    
}
