package TeleMed.TeleMed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TeleMed.TeleMed.Models.MedicalStore;
import TeleMed.TeleMed.repository.MedicalStoreRepository;

@Service
public class MedicalStoreService {

    @Autowired
    private MedicalStoreRepository medicalStoreRepository;

    // Create a new medical store
    public MedicalStore createMedicalStore(MedicalStore medicalStore) {
        return medicalStoreRepository.save(medicalStore);
    }

    // Get all medical stores
    public List<MedicalStore> getAllMedicalStores() {
        return medicalStoreRepository.findAll();
    }

    // Get a single medical store by ID
    public MedicalStore getMedicalStoreById(String id) {
        return medicalStoreRepository.findById(id)
                .orElseThrow();
    }


    // Update a medical store by ID
    public MedicalStore updateMedicalStore(String id, MedicalStore updatedMedicalStore) {
        // Check if the medical store exists
        return medicalStoreRepository.findById(id).map(existingMedicalStore -> {
            // Update fields only if they are not null in the input
            if (updatedMedicalStore.getName() != null) {
                existingMedicalStore.setName(updatedMedicalStore.getName());
            }
            if (updatedMedicalStore.getEmail() != null) {
                existingMedicalStore.setEmail(updatedMedicalStore.getEmail());
            }
            if (updatedMedicalStore.getAddress() != null) {
                existingMedicalStore.setAddress(updatedMedicalStore.getAddress());
            }
            if (updatedMedicalStore.getPhone() != null) {
                existingMedicalStore.setPhone(updatedMedicalStore.getPhone());
            }
            // Add other fields as needed
            
            // Save the updated entity
            return medicalStoreRepository.save(existingMedicalStore);
        }).orElse(null); // Return null if the medical store does not exist
    }


    // Delete a medical store by ID
    public boolean deleteMedicalStore(String id) {
        if (medicalStoreRepository.existsById(id)) {
            medicalStoreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public MedicalStore saveMedicalStore(MedicalStore store) {
        return medicalStoreRepository.save(store);
    }

}
