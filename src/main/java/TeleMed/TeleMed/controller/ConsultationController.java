package TeleMed.TeleMed.controller;

import TeleMed.TeleMed.Models.Consultation;
import TeleMed.TeleMed.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    // Endpoint to create a new consultation
    @PostMapping("/create")
    public ResponseEntity<?> createConsultation(@RequestBody Consultation consultation) {
        try {
            // Step 1: Save the consultation
            Consultation newConsultation = consultationService.createConsultation(consultation);

            // Step 2: Add the consultation ID to the patient and doctor (update their consultations list)
            consultationService.addConsultationToPatientAndDoctor(newConsultation);

            return ResponseEntity.ok(newConsultation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating consultation: " + e.getMessage());
        }
    }


    // Endpoint to get a consultation by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getConsultationById(@PathVariable String id) {
        try {
            Consultation consultation = consultationService.getConsultationById(id);
            return ResponseEntity.ok(consultation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consultation not found with ID: " + id);
        }
    }

    // Endpoint to get all consultations for a specific patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getConsultationsByPatientId(@PathVariable String patientId) {
        try {
            return ResponseEntity.ok(consultationService.getConsultationsByPatientId(patientId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No consultations found for patient ID: " + patientId);
        }
    }

    // Endpoint to get all consultations for a specific doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getConsultationsByDoctorId(@PathVariable String doctorId) {
        try {
            return ResponseEntity.ok(consultationService.getConsultationsByDoctorId(doctorId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No consultations found for doctor ID: " + doctorId);
        }
    }

    // Endpoint to update a consultation
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateConsultation(@PathVariable String id, @RequestBody Consultation consultationDetails) {
        try {
            Consultation updatedConsultation = consultationService.updateConsultation(id, consultationDetails);
            return ResponseEntity.ok(updatedConsultation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating consultation: " + e.getMessage());
        }
    }

    // Endpoint to delete a consultation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteConsultation(@PathVariable String id) {
        try {
            consultationService.deleteConsultation(id);
            return ResponseEntity.ok("Consultation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting consultation: " + e.getMessage());
        }
    }
}
