package TeleMed.TeleMed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TeleMed.TeleMed.Models.MedicalStore;
import TeleMed.TeleMed.Models.Medicine;
import TeleMed.TeleMed.Models.MedicineOrder;
import TeleMed.TeleMed.Models.Patient;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.repository.MedicalStoreRepository;
import TeleMed.TeleMed.repository.MedicineOrderRepository;
import TeleMed.TeleMed.repository.MedicineRepository;
import TeleMed.TeleMed.repository.PatientRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MedicineOrderService {

    @Autowired
    private MedicineOrderRepository medicineOrderRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicalStoreRepository medicalStoreRepository;

    @Autowired
    private JwtUtil jwtUtil;  // Assuming you have a JwtUtil to extract information from JWT

    @Autowired
    private PatientRepository patientRepository;
 // Method to create an order
 // Method to create an order
    public MedicineOrder createOrder(MedicineOrder order, String token) {
        // Extract the patientId from the JWT token
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        String patientId = jwtUtil.extractUserId(token);
        System.out.println("------------Extracted patientId from JWT: " + patientId);

        // Set the patientId if it's not already set
        if (order.getPatientId() == null) {
            order.setPatientId(patientId);
        }

        // Check if the medicine exists
        Optional<Medicine> medicineOptional = medicineRepository.findById(order.getMedicineId());
        if (!medicineOptional.isPresent()) {
            throw new RuntimeException("Medicine not found with ID: " + order.getMedicineId());
        }

        // Save the order first
        order.setStatus("Pending"); // Default status for the order
        MedicineOrder savedOrder = medicineOrderRepository.save(order);

        // Fetch the patient and update the orders list
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (!patientOptional.isPresent()) {
            throw new RuntimeException("Patient not found with ID: " + patientId);
        }

        Patient patient = patientOptional.get();
        if (patient.getOrder() == null) {
            patient.setOrder(new ArrayList<>());
        }

        // Add the saved order's ID to the patient's list of orders
        patient.getOrder().add(savedOrder.getId());

        // Save the patient after associating the new order
        patientRepository.save(patient);

        // Fetch the medical store and update the orders list
        Optional<MedicalStore> storeOptional = medicalStoreRepository.findById(order.getStoreId());
        if (!storeOptional.isPresent()) {
            throw new RuntimeException("Medical store not found with ID: " + order.getStoreId());
        }

        MedicalStore store = storeOptional.get();
        if (store.getOrder() == null) {
            store.setOrder(new ArrayList<>());
        }

        // Add the saved order's ID to the store's list of orders
        store.getOrder().add(savedOrder.getId());

        // Save the medical store after associating the new order
        medicalStoreRepository.save(store);

        return savedOrder;  // Return the saved order
    }


    public MedicineOrder getOrderById(String orderId) {
        // Fetch the order from the repository or database
        return medicineOrderRepository.findById(orderId).orElse(null);  // Assuming you're using a repository
    }


    // Method to get orders by patientId
    public List<MedicineOrder> getOrdersByPatientId(String patientId) {
        return medicineOrderRepository.findByPatientId(patientId);
    }

    // Method to get orders by storeId
    public List<MedicineOrder> getOrdersByStoreId(String storeId) {
        return medicineOrderRepository.findByStoreId(storeId);
    }

    // Method to update an existing order
    public MedicineOrder updateOrder(String orderId, MedicineOrder updatedOrder) {
        // Find the existing order by its ID
        Optional<MedicineOrder> existingOrderOptional = medicineOrderRepository.findById(orderId);
        if (!existingOrderOptional.isPresent()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }

        MedicineOrder existingOrder = existingOrderOptional.get();

        // Update the order's fields with the new data
        if (updatedOrder.getMedicineId() != null) {
            existingOrder.setMedicineId(updatedOrder.getMedicineId());
        }
        if (updatedOrder.getQuantity() > 0) {
            existingOrder.setQuantity(updatedOrder.getQuantity());
        }
        if (updatedOrder.getStoreId() != null) {
            existingOrder.setStoreId(updatedOrder.getStoreId());
        }
        if (updatedOrder.getStatus() != null) {
            existingOrder.setStatus(updatedOrder.getStatus());
        }

        // Save the updated order back to the repository
        return medicineOrderRepository.save(existingOrder);
    }

    // Method to delete an order by ID
    public void deleteOrder(String orderId) {
        Optional<MedicineOrder> orderOptional = medicineOrderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }

        // Delete the order
        medicineOrderRepository.delete(orderOptional.get());
    }

    // Method to delete orders by patient ID (if needed)
    public void deleteOrdersByPatientId(String patientId) {
        List<MedicineOrder> orders = medicineOrderRepository.findByPatientId(patientId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for Patient ID: " + patientId);
        }

        // Delete all orders for the patient
        medicineOrderRepository.deleteAll(orders);
    }

    // Method to delete orders by store ID (if needed)
    public void deleteOrdersByStoreId(String storeId) {
        List<MedicineOrder> orders = medicineOrderRepository.findByStoreId(storeId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for Store ID: " + storeId);
        }

        // Delete all orders for the store
        medicineOrderRepository.deleteAll(orders);
    }
}
