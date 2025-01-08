package TeleMed.TeleMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TeleMed.TeleMed.Models.MedicalStore;
import TeleMed.TeleMed.Models.Medicine;
import TeleMed.TeleMed.service.MedicalStoreService;
import TeleMed.TeleMed.service.MedicineService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicalStoreService medicineStoreService;

    // Create a new medicine
    @PostMapping
    @PreAuthorize("hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        List<String> storeIds = medicine.getStoreId();  // List of store IDs
        Medicine savedMedicine = medicineService.createMedicine(medicine);
        // Loop over each storeId and process
        for (String storeId : storeIds) {
            // Retrieve the store by ID
            MedicalStore store = medicineStoreService.getMedicalStoreById(storeId);

            if (store == null) {
                // Return 404 if any of the stores are not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Initialize the availableMedicines list if it is null
            if (store.getAvailableMedicines() == null) {
                store.setAvailableMedicines(new ArrayList<>());
            }

            // Add the saved medicine to the store's available medicines list
            store.getAvailableMedicines().add(medicine);

            // Save the updated store
            medicineStoreService.saveMedicalStore(store);
        }

        // Save the new medicine
        

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicine);
    }


    // Get all medicines
    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineService.getAllMedicines();
        return ResponseEntity.ok(medicines);
    }

    // Get a specific medicine by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable String id) {
        try {
            Medicine medicine = medicineService.getMedicineById(id);
            return ResponseEntity.ok(medicine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get medicines by store ID
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')or hasRole('ROLE_PHARMACIST')or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<Medicine>> getMedicinesByStoreId(@PathVariable String storeId) {
        List<Medicine> medicines = medicineService.getMedicinesByStoreId(storeId);
        return ResponseEntity.ok(medicines);
    }

    // Update an existing medicine
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable String id, @RequestBody Medicine medicine) {
        try {
            Medicine updatedMedicine = medicineService.updateMedicine(id, medicine);
            return ResponseEntity.ok(updatedMedicine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
 // Get a specific medicine by medicine ID and store ID
    @GetMapping("/{medicineId}/store/{storeId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PHARMACIST') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<Medicine> getMedicineByIdAndStoreId(@PathVariable String medicineId, @PathVariable String storeId) {
        try {
            Medicine medicine = medicineService.getMedicineByIdAndStoreId(medicineId, storeId);
            return ResponseEntity.ok(medicine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    // Delete a medicine by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable String id) {
        try {
            medicineService.deleteMedicine(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
