package TeleMed.TeleMed.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TeleMed.TeleMed.Models.Consultation;
import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.Models.Prescription;
import TeleMed.TeleMed.service.ConsultationService;
import TeleMed.TeleMed.service.PatientService;
import TeleMed.TeleMed.service.PrescriptionService;

import java.util.List;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private PatientService patientService;

    // Create a new prescription
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_DOCTOR')")  // Only PATIENT or DOCTOR roles can access
    public ResponseEntity<?> createPrescription(@RequestBody Prescription prescription) {
        try {
            // Validate the consultation
            String consultationId = prescription.getConsultationId();
            Consultation consultation = consultationService.getConsultationById(consultationId);
            if (consultation == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Consultation not found for ID: " + consultationId);
            }

            // Save the prescription
            Prescription newPrescription = prescriptionService.createPrescription(prescription);

            // Update the consultation with the prescription ID
            consultation.setPrescriptionId(newPrescription.getId());
            consultationService.saveConsultation(consultation);

            // Fetch the patient from the consultation
            String patientId = consultation.getPatientId();
            Patient patient = patientService.getPatientById(patientId);
            if (patient == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Patient not found for ID: " + patientId);
            }

            // Add the prescription ID to the patient's medical history
            if (patient.getMedicalHistory() == null) {
                patient.setMedicalHistory(new ArrayList<>());
            }
            patient.getMedicalHistory().add(newPrescription.getId());

            // Save the updated patient
            patientService.savePatient(patient);

            return ResponseEntity.ok(newPrescription);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating prescription: " + e.getMessage());
        }
    }


    // Get all prescriptions
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    // Get a prescription by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescriptionById(@PathVariable String id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id).orElse(null);
        if (prescription == null) {
            return ResponseEntity.status(404).body("Prescription not found");
        }
        return ResponseEntity.ok(prescription);
    }

    // Update a prescription by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescription(@PathVariable String id, @RequestBody Prescription updatedPrescription) {
        Prescription updated = prescriptionService.updatePrescription(id, updatedPrescription);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Prescription not found for update");
        }
    }

    // Delete a prescription by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable String id) {
        if (prescriptionService.deletePrescription(id)) {
            return ResponseEntity.ok("Prescription deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Prescription not found for deletion");
        }
    }

    // Get prescriptions by store ID
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByStoreId(@PathVariable String storeId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByStoreId(storeId);
        return ResponseEntity.ok(prescriptions);
    }
}
