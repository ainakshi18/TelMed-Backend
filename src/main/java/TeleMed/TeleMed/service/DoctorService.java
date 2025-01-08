package TeleMed.TeleMed.service;

import TeleMed.TeleMed.Models.Consultation;
import TeleMed.TeleMed.Models.Doctor;
import TeleMed.TeleMed.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Create or update a doctor
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get a doctor by ID
    public Doctor findById(String id) {
        return doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    // Update a doctor's details
    public Doctor updateDoctor(String id, Doctor doctorDetails) {
        Doctor doctor = findById(id);

        if (doctorDetails.getName() != null) doctor.setName(doctorDetails.getName());
        if (doctorDetails.getSpecialization() != null) doctor.setSpecialization(doctorDetails.getSpecialization());
        if (doctorDetails.getPhone() != null) doctor.setPhone(doctorDetails.getPhone());
        if (doctorDetails.getEmail() != null) doctor.setEmail(doctorDetails.getEmail());
        if (doctorDetails.getAvailability() != null) doctor.setAvailability(doctorDetails.getAvailability());
        if (doctorDetails.getConsultations() != null) doctor.setConsultations(doctorDetails.getConsultations());

        return doctorRepository.save(doctor);
    }

    // Delete a doctor
    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }

    // Get all available doctors
    public List<Doctor> getDoctorsByAvailability(String timeSlot) {
        return doctorRepository.findByAvailability(timeSlot);
    }
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }


}
