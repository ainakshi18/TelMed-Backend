package TeleMed.TeleMed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import TeleMed.TeleMed.Models.Medicine;
import TeleMed.TeleMed.Models.MedicineOrder;

public interface MedicineRepository extends MongoRepository<Medicine, String> {
    // You can define custom queries here if needed
	 List<Medicine> findByStoreId(String storeId);
	 Optional<Medicine> findByIdAndStoreId(String medicineId, String storeId);

}
