package TeleMed.TeleMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TeleMed.TeleMed.Models.MedicineRequest;
import TeleMed.TeleMed.service.MedicineRequestService;

@RestController
@RequestMapping("/api/medicine-request")
public class MedicineRequestController {

    @Autowired
    private MedicineRequestService medicineRequestService;

    // Create a new medicine request
    @PostMapping
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> createRequest(@RequestBody MedicineRequest request, @RequestHeader("Authorization") String token) {
        try {
            MedicineRequest createdRequest = medicineRequestService.createRequest(request, token);
            return ResponseEntity.ok(createdRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error creating request: " + e.getMessage());
        }
    }
 // Get a request by ID
    @GetMapping("/{requestId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> getRequestById(@PathVariable String requestId) {
        try {
            MedicineRequest request = medicineRequestService.getRequestById(requestId);
            if (request == null) {
                return ResponseEntity.notFound().build();  // Handle not found
            }
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error fetching request: " + e.getMessage());
        }
    }

    // Get all requests for a patient by patientId
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')or hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> getRequestsByPatientId(@PathVariable String patientId) {
        try {
            return ResponseEntity.ok(medicineRequestService.getRequestsByPatientId(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error fetching requests: " + e.getMessage());
        }
    }

    // Get all requests for a store by storeId
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> getRequestsByStoreId(@PathVariable String storeId) {
        try {
            return ResponseEntity.ok(medicineRequestService.getRequestsByStoreId(storeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error fetching requests: " + e.getMessage());
        }
    }

    // Update an existing medicine request
    @PutMapping("/{requestId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')or hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> updateRequest(@PathVariable String requestId, @RequestBody MedicineRequest updatedRequest) {
        try {
            MedicineRequest updated = medicineRequestService.updateRequest(requestId, updatedRequest);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error updating request: " + e.getMessage());
        }
    }

    // Delete a medicine request
    @DeleteMapping("/{requestId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')or hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> deleteRequest(@PathVariable String requestId) {
        try {
            medicineRequestService.deleteRequest(requestId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting request: " + e.getMessage());
        }
    }
}
