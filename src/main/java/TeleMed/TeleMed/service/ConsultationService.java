package TeleMed.TeleMed.service;

import TeleMed.TeleMed.Models.Consultation;
import TeleMed.TeleMed.Models.Doctor;
import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.repository.ConsultationRepository;
import TeleMed.TeleMed.repository.DoctorRepository;
import TeleMed.TeleMed.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationService {

	@Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Create a new consultation
    public Consultation createConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    // Create a new consultation
    public void addConsultationToPatientAndDoctor(Consultation consultation) {
        // Step 1: Update the patient document
        Patient patient = patientRepository.findById(consultation.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        // Initialize the consultations list if it's null
        if (patient.getConsultation() == null) {
            patient.setConsultation(new ArrayList<>());
        }
        
        // Add the consultation ID to the patient's consultations list
        patient.getConsultation().add(consultation.getId());
        patientRepository.save(patient);

        // Step 2: Update the doctor document
        Doctor doctor = doctorRepository.findById(consultation.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        // Initialize the consultations list if it's null
        if (doctor.getConsultations() == null) {
            doctor.setConsultations(new ArrayList<>());
        }
        
        // Add the consultation ID to the doctor's consultations list
        doctor.getConsultations().add(consultation.getId());
        doctorRepository.save(doctor);
    }

    // Get all consultations
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    // Get a consultation by ID
    public Consultation getConsultationById(String id) {
        return consultationRepository.findById(id).orElseThrow(() -> new RuntimeException("Consultation not found"));
    }

    // Get consultations by patient ID
    public List<Consultation> getConsultationsByPatientId(String patientId) {
        return consultationRepository.findByPatientId(patientId);
    }

    // Get consultations by doctor ID
    public List<Consultation> getConsultationsByDoctorId(String doctorId) {
        return consultationRepository.findByDoctorId(doctorId);
    }

    // Update consultation details
    public Consultation updateConsultation(String id, Consultation consultationDetails) {
        Consultation consultation = getConsultationById(id);

        if (consultationDetails.getPatientId() != null) consultation.setPatientId(consultationDetails.getPatientId());
        if (consultationDetails.getDoctorId() != null) consultation.setDoctorId(consultationDetails.getDoctorId());
        if (consultationDetails.getDate() != null) consultation.setDate(consultationDetails.getDate());
        if (consultationDetails.getDiagnosis() != null) consultation.setDiagnosis(consultationDetails.getDiagnosis());
        if (consultationDetails.getPrescriptionId() != null) consultation.setPrescriptionId(consultationDetails.getPrescriptionId());

        return consultationRepository.save(consultation);
    }

    // Delete a consultation
    public void deleteConsultation(String id) {
        consultationRepository.deleteById(id);
    }
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }
}
