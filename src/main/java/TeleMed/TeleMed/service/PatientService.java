package TeleMed.TeleMed.service;

import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public Patient updatePatient(String id, Patient patientDetails) {
        Patient patient = getPatientById(id);

        // Update only if the value is not null
        if (patientDetails.getName() != null) {
            patient.setName(patientDetails.getName());
        }
        if (patientDetails.getAge() != 0) {
            patient.setAge(patientDetails.getAge());
        }
        if (patientDetails.getGender() != null) {
            patient.setGender(patientDetails.getGender());
        }
        if (patientDetails.getAddress() != null) {
            patient.setAddress(patientDetails.getAddress());
        }
        if (patientDetails.getPhone() != null) {
            patient.setPhone(patientDetails.getPhone());
        }
        if (patientDetails.getEmail() != null) {
            patient.setEmail(patientDetails.getEmail());
        }
        if (patientDetails.getMedicalHistory() != null) {
            patient.setMedicalHistory(patientDetails.getMedicalHistory());
        }
        if (patientDetails.getCurrentConsultationId() != null) {
            patient.setCurrentConsultationId(patientDetails.getCurrentConsultationId());
        }

        return patientRepository.save(patient);
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

    // Find a patient by ID
    public Patient findById(String id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }
    
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
