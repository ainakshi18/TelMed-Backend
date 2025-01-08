package TeleMed.TeleMed.controller;

import TeleMed.TeleMed.Models.Doctor;
import TeleMed.TeleMed.Models.User;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.service.DoctorService;
import TeleMed.TeleMed.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint to create a new doctor (only accessible to ROLE_USER or higher)
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")  // Only PATIENT or DOCTOR roles can access
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor, @RequestHeader("Authorization") String token) {
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

            // Set the userId, name, email, and role for the doctor
            doctor.setId(user.getId());
            doctor.setName(user.getName()); // Assuming the name is in the `fullname` field of the User
            doctor.setEmail(user.getEmail());

            // Create the new doctor in the system
            Doctor newDoctor = doctorService.createDoctor(doctor);

            return ResponseEntity.ok(newDoctor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating doctor: " + e.getMessage());
        }
    }

    // Endpoint to get doctor details by ID (optional)
//    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR')")  // Only PATIENT or DOCTOR roles can access
    public ResponseEntity<?> getDoctorById(@PathVariable String id) {
        try {
            Doctor doctor = doctorService.findById(id);
            return ResponseEntity.ok(doctor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found with ID: " + id);
        }
    }

    // Endpoint to update doctor's details (only if data is not null)
    //@PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR')")  // Only PATIENT or DOCTOR roles can access
    public ResponseEntity<?> updateDoctor(@PathVariable String id, @RequestBody Doctor doctorDetails) {
        try {
            Doctor existingDoctor = doctorService.findById(id);
            
            if (doctorDetails.getName() != null) existingDoctor.setName(doctorDetails.getName());
            if (doctorDetails.getSpecialization() != null) existingDoctor.setSpecialization(doctorDetails.getSpecialization());
            if (doctorDetails.getPhone() != null) existingDoctor.setPhone(doctorDetails.getPhone());
            if (doctorDetails.getEmail() != null) existingDoctor.setEmail(doctorDetails.getEmail());
            if (doctorDetails.getAvailability() != null) existingDoctor.setAvailability(doctorDetails.getAvailability());
            if (doctorDetails.getConsultations() != null) existingDoctor.setConsultations(doctorDetails.getConsultations());

            Doctor updatedDoctor = doctorService.createDoctor(existingDoctor);

            return ResponseEntity.ok(updatedDoctor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating doctor: " + e.getMessage());
        }
    }
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_DOCTOR')")  // Only PATIENT or DOCTOR roles can access
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctors);
    }
}
