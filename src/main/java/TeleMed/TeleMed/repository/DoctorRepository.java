package TeleMed.TeleMed.repository;

import TeleMed.TeleMed.Models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {

    List<Doctor> findByAvailability(String timeSlot); // Custom query to get doctors based on availability
}
