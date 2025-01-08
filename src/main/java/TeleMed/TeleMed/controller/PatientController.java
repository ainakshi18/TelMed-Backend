package TeleMed.TeleMed.controller;

import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.Models.User;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.service.PatientService;
import TeleMed.TeleMed.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint to create a new patient (only accessible to ROLE_USER or higher)
    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient, @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the "Bearer " prefix
            token = token.startsWith("Bearer ") ? token.substring(7) : token;

            // Extract the userId from JWT token
            String userId = jwtUtil.extractUserId(token);
            System.out.println("----------------this is userId--------" + userId);  // Log the extracted userId

            // Fetch the user details using the extracted userId
            User user = userService.findById(userId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Set the userId, name, email, and role for the patient
            patient.setId(user.getId());
            patient.setName(user.getName()); // Assuming the name is in the `fullname` field of the User
            patient.setEmail(user.getEmail());
            patient.setRole(user.getUserRole().toString()); // Assuming `UserRole` is an enum and needs to be converted to a string

            // Create the new patient in the system
            Patient newPatient = patientService.createPatient(patient);

            return ResponseEntity.ok(newPatient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating patient: " + e.getMessage());
        }
    }

    // Endpoint to get patient details by ID (optional)
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable String id) {
        try {
            Patient patient = patientService.findById(id);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with ID: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable String id, @RequestBody Patient patientDetails, @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the "Bearer " prefix
            token = token.startsWith("Bearer ") ? token.substring(7) : token;

            // Extract the userId from JWT token
            String userId = jwtUtil.extractUserId(token);
            System.out.println("----------------this is userId--------" + userId);  // Log the extracted userId

            // Fetch the user details using the extracted userId
            User user = userService.findById(userId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Check if the patient ID matches the logged-in user's ID (only if patientDetails.getId() is not null)
            if (patientDetails.getId() != null && !patientDetails.getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this patient");
            }

            // Update the patient details using the service
            Patient updatedPatient = patientService.updatePatient(id, patientDetails);

            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating patient: " + e.getMessage());
        }
    }

}
