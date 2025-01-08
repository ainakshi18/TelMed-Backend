package TeleMed.TeleMed.repository;

import TeleMed.TeleMed.Models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
}
