package TeleMed.TeleMed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TeleMed.TeleMed.Models.Medicine;
import TeleMed.TeleMed.repository.MedicineRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    // Create a new medicine
    public Medicine createMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    // Get all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Get a medicine by its ID
    public Medicine getMedicineById(String id) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        return medicine.orElseThrow(() -> new RuntimeException("Medicine not found with ID: " + id)); // Handle the case where the medicine is not found
    }

    // Get medicines by store ID
    public List<Medicine> getMedicinesByStoreId(String storeId) {
        return medicineRepository.findByStoreId(storeId); // Using the custom query in the repository
    }

    // Update an existing medicine
    public Medicine updateMedicine(String id, Medicine medicine) {
        if (!medicineRepository.existsById(id)) {
            throw new RuntimeException("Medicine not found with ID: " + id);
        }

        // Retrieve the existing medicine by ID
        Medicine existingMedicine = medicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Medicine not found with ID: " + id));

        // Update only the necessary fields
        if (medicine.getName() != null) {
            existingMedicine.setName(medicine.getName());
        }
        if (medicine.getQuantity() > 0) {
            existingMedicine.setQuantity(medicine.getQuantity());
        }
        if (medicine.getPrice() > 0) {
            existingMedicine.setPrice(medicine.getPrice());
        }
        if (medicine.getExpiryDate() != null) {
            existingMedicine.setExpiryDate(medicine.getExpiryDate());
        }
        if (medicine.getDescription() != null) {
            existingMedicine.setDescription(medicine.getDescription());
        }
        if (medicine.getImageUrl() != null) {
            existingMedicine.setImageUrl(medicine.getImageUrl());
        }

        // Save and return the updated medicine
        return medicineRepository.save(existingMedicine);
    }
    public Medicine getMedicineByIdAndStoreId(String medicineId, String storeId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        if (medicine.getStoreId().contains(storeId)) {
            return medicine;
        } else {
            throw new RuntimeException("Medicine not available in the specified store");
        }
    }


    // Delete a medicine by its ID
    public void deleteMedicine(String id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
        } else {
            throw new RuntimeException("Medicine not found with ID: " + id);
        }
    }
}
