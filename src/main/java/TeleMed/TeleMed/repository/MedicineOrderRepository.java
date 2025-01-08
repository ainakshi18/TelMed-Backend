package TeleMed.TeleMed.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import TeleMed.TeleMed.Models.MedicineOrder;
import java.util.List;

public interface MedicineOrderRepository extends MongoRepository<MedicineOrder, String> {
    List<MedicineOrder> findByPatientId(String patientId);
    List<MedicineOrder> findByStoreId(String storeId);
}
