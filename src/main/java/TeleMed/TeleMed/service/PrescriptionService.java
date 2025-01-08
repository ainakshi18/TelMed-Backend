package TeleMed.TeleMed.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TeleMed.TeleMed.Models.Prescription;
import TeleMed.TeleMed.repository.PrescriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Create a new prescription
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    // Get all prescriptions
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    // Get a prescription by ID
    public Optional<Prescription> getPrescriptionById(String id) {
        return prescriptionRepository.findById(id);
    }

    // Update a prescription by ID
    public Prescription updatePrescription(String id, Prescription updatedPrescription) {
        return prescriptionRepository.findById(id).map(existingPrescription -> {
            if (updatedPrescription.getConsultationId() != null) {
                existingPrescription.setConsultationId(updatedPrescription.getConsultationId());
            }
            if (updatedPrescription.getMedicines() != null) {
                existingPrescription.setMedicines(updatedPrescription.getMedicines());
            }
            if (updatedPrescription.getStatus() != null) {
                existingPrescription.setStatus(updatedPrescription.getStatus());
            }
            if (updatedPrescription.getStoreId() != null) {
                existingPrescription.setStoreId(updatedPrescription.getStoreId());
            }
            return prescriptionRepository.save(existingPrescription);
        }).orElse(null);
    }

    // Delete a prescription by ID
    public boolean deletePrescription(String id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get prescriptions by store ID
    public List<Prescription> getPrescriptionsByStoreId(String storeId) {
        return prescriptionRepository.findByStoreId(storeId);
    }
}
