package TeleMed.TeleMed.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import TeleMed.TeleMed.Models.Prescription;

import java.util.List;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
    List<Prescription> findByStoreId(String storeId);
}
