package TeleMed.TeleMed.repository;

import TeleMed.TeleMed.Models.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConsultationRepository extends MongoRepository<Consultation, String> {

    // Custom query methods for fetching consultations by patient or doctor
    List<Consultation> findByPatientId(String patientId);
    List<Consultation> findByDoctorId(String doctorId);
}
