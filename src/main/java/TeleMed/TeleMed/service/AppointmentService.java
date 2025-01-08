package TeleMed.TeleMed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TeleMed.TeleMed.Models.Appointment;
import TeleMed.TeleMed.Models.Doctor;
import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.repository.AppointmentRepository;
import TeleMed.TeleMed.repository.DoctorRepository;
import TeleMed.TeleMed.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    // Create a new appointment
    public Appointment createAppointment(Appointment appointment) {
        // Save the appointment to the database
        return appointmentRepository.save(appointment);
    }

    public void updatePatientAndDoctorAppointments(Appointment appointment) {
        // Update the Doctor's appointments list
        Doctor doctor = doctorRepository.findById(appointment.getDoctorId())
                                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        if (doctor.getAppointments() == null) {
            doctor.setAppointments(new ArrayList<>());
        }
        doctor.getAppointments().add(appointment.getId());
        doctorRepository.save(doctor);

        // Update the Patient's appointments list
        Patient patient = patientRepository.findById(appointment.getPatientId())
                                          .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patient.getAppointments() == null) {
            patient.setAppointments(new ArrayList<>());
        }
        patient.getAppointments().add(appointment.getId());
        patientRepository.save(patient);
    }
    // Get all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    

    // Get a single appointment by ID
    public Optional<Appointment> getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }
    // Get appointments by Patient ID
    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Get appointments by Doctor ID
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment updateAppointment(String id, Appointment updatedAppointment) {
        Optional<Appointment> existingAppointment = appointmentRepository.findById(id);

        if (existingAppointment.isPresent()) {
            Appointment appointment = existingAppointment.get();

            // Update fields if they are not null
            if (updatedAppointment.getStatus() != null) {
                appointment.setStatus(updatedAppointment.getStatus());
            }
        
            if (updatedAppointment.getDoctorId() != null) {
                appointment.setDoctorId(updatedAppointment.getDoctorId());
            }
            if (updatedAppointment.getTime() != null) {
                appointment.setTime(updatedAppointment.getTime());
            }
   
            // Save the updated appointment
            return appointmentRepository.save(appointment);
    }
		return updatedAppointment;
}
}
