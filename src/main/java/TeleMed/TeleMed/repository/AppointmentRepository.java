package TeleMed.TeleMed.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import TeleMed.TeleMed.Models.Appointment;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    // Find appointments by patient ID
    List<Appointment> findByPatientId(String patientId);

    // Find appointments by doctor ID
    List<Appointment> findByDoctorId(String doctorId);
}
