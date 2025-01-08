package TeleMed.TeleMed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TeleMed.TeleMed.Models.MedicineRequest;
import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.Models.MedicalStore;
import TeleMed.TeleMed.Models.Medicine;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.repository.MedicineRequestRepository;
import TeleMed.TeleMed.repository.PatientRepository;
import TeleMed.TeleMed.repository.MedicalStoreRepository;
import TeleMed.TeleMed.repository.MedicineRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MedicineRequestService {

    @Autowired
    private MedicineRequestRepository medicineRequestRepository;

    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalStoreRepository medicalStoreRepository;
    @Autowired
    private JwtUtil jwtUtil;  // Assuming you have a JwtUtil to extract information from JWT

 // Method to create a medicine request
    public MedicineRequest createRequest(MedicineRequest request, String token) {
        // Extract the patientId from the JWT token
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        String patientId = jwtUtil.extractUserId(token);
        System.out.println("------------Extracted patientId from JWT: " + patientId);

        // Set the patientId if it's not already set
        if (request.getPatientId() == null) {
            request.setPatientId(patientId);
        }

        // Fetch the patient and save the request
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (!patientOptional.isPresent()) {
            throw new RuntimeException("Patient not found with ID: " + patientId);
        }

        // Save the medicine request
        MedicineRequest savedRequest = medicineRequestRepository.save(request);

        // Update the patient's list of requests by adding the new request ID
        Patient patient = patientOptional.get();
        if (patient.getRequest() == null) {
            patient.setRequest(new ArrayList<>());
        }
        patient.getRequest().add(savedRequest.getId()); // Add the request ID to the list
        patientRepository.save(patient); // Save the updated patient record

        // Fetch the medical store and update the requests list
        Optional<MedicalStore> storeOptional = medicalStoreRepository.findById(request.getStoreId());
        if (!storeOptional.isPresent()) {
            throw new RuntimeException("Medical store not found with ID: " + request.getStoreId());
        }

        // Add the saved request's ID to the store's list of requests
        MedicalStore store = storeOptional.get();
        if (store.getRequest() == null) {
            store.setRequest(new ArrayList<>());
        }
        store.getRequest().add(savedRequest.getId()); // Add the request ID to the store's request list
        medicalStoreRepository.save(store); // Save the updated medical store

        return savedRequest; // Return the saved request
    }



    // Method to get requests by patientId
    public List<MedicineRequest> getRequestsByPatientId(String patientId) {
        return medicineRequestRepository.findByPatientId(patientId);
    }

    // Method to get requests by storeId
    public List<MedicineRequest> getRequestsByStoreId(String storeId) {
        return medicineRequestRepository.findByStoreId(storeId);
    }

    // Method to update an existing medicine request
    public MedicineRequest updateRequest(String requestId, MedicineRequest updatedRequest) {
        // Find the existing request by its ID
        Optional<MedicineRequest> existingRequestOptional = medicineRequestRepository.findById(requestId);
        if (!existingRequestOptional.isPresent()) {
            throw new RuntimeException("Request not found with ID: " + requestId);
        }

        MedicineRequest existingRequest = existingRequestOptional.get();

        // Update the request's fields with the new data
        if (updatedRequest.getMedicineId() != null) {
            existingRequest.setMedicineId(updatedRequest.getMedicineId());
        }
        if (updatedRequest.getQuantity() > 0) {
            existingRequest.setQuantity(updatedRequest.getQuantity());
        }
        if (updatedRequest.getStoreId() != null) {
            existingRequest.setStoreId(updatedRequest.getStoreId());
        }
        if (updatedRequest.getStatus() != null) {
            existingRequest.setStatus(updatedRequest.getStatus());
        }

        // Save the updated request back to the repository
        return medicineRequestRepository.save(existingRequest);
    }
    public MedicineRequest getRequestById(String requestId) {
        // Fetch the request from the repository or database
        return medicineRequestRepository.findById(requestId).orElse(null);  // Assuming you're using a repository
    }


    // Method to delete a medicine request by ID
    public void deleteRequest(String requestId) {
        Optional<MedicineRequest> requestOptional = medicineRequestRepository.findById(requestId);
        if (!requestOptional.isPresent()) {
            throw new RuntimeException("Request not found with ID: " + requestId);
        }

        // Delete the request
        medicineRequestRepository.delete(requestOptional.get());
    }

    // Method to delete requests by patient ID (if needed)
    public void deleteRequestsByPatientId(String patientId) {
        List<MedicineRequest> requests = medicineRequestRepository.findByPatientId(patientId);
        if (requests.isEmpty()) {
            throw new RuntimeException("No requests found for Patient ID: " + patientId);
        }

        // Delete all requests for the patient
        medicineRequestRepository.deleteAll(requests);
    }

    // Method to delete requests by store ID (if needed)
    public void deleteRequestsByStoreId(String storeId) {
        List<MedicineRequest> requests = medicineRequestRepository.findByStoreId(storeId);
        if (requests.isEmpty()) {
            throw new RuntimeException("No requests found for Store ID: " + storeId);
        }

        // Delete all requests for the store
        medicineRequestRepository.deleteAll(requests);
    }
}
