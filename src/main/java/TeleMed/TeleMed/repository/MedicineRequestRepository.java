package TeleMed.TeleMed.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import TeleMed.TeleMed.Models.MedicineRequest;
import java.util.List;

public interface MedicineRequestRepository extends MongoRepository<MedicineRequest, String> {
    List<MedicineRequest> findByPatientId(String patientId);
    List<MedicineRequest> findByStoreId(String storeId);
}
