package TeleMed.TeleMed.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import TeleMed.TeleMed.Models.MedicalStore;

@Repository
public interface MedicalStoreRepository extends MongoRepository<MedicalStore, String> {
    // You can add custom queries here if needed
}
