package TeleMed.TeleMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TeleMed.TeleMed.Models.MedicineOrder;
import TeleMed.TeleMed.service.MedicineOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class MedicineOrderController {

    @Autowired
    private MedicineOrderService medicineOrderService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> createOrder(@RequestBody MedicineOrder order, @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Received Token: " + token);  // Log the token received
            MedicineOrder createdOrder = medicineOrderService.createOrder(order, token);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());  // Log the error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error creating order: " + e.getMessage());
        }
    }
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PHARMACIST') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        try {
            MedicineOrder order = medicineOrderService.getOrderById(orderId);
            if (order == null) {
                return ResponseEntity.notFound().build();  // Handle not found
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error fetching order: " + e.getMessage());
        }
    }

    // Get all orders by patient ID
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<List<MedicineOrder>> getOrdersByPatientId(@PathVariable String patientId) {
        try {
            List<MedicineOrder> orders = medicineOrderService.getOrdersByPatientId(patientId);
            if (orders == null) {
                return ResponseEntity.notFound().build();  // Handle not found
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Handle server errors
        }
    }


    // Get all orders by store ID
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ROLE_PHARMACIST') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<MedicineOrder>> getOrdersByStoreId(@PathVariable String storeId) {
        try {
            List<MedicineOrder> orders = medicineOrderService.getOrdersByStoreId(storeId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null);
        }
    }

    // Update order by ID
    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PHARMACIST') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<?> updateOrder(@PathVariable String orderId, @RequestBody MedicineOrder updatedOrder) {
        try {
            MedicineOrder order = medicineOrderService.updateOrder(orderId, updatedOrder);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error updating order: " + e.getMessage());
        }
    }

    // Delete order by ID
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')or hasRole('ROLE_PHARMACIST')")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderId) {
        try {
            medicineOrderService.deleteOrder(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting order: " + e.getMessage());
        }
    }
}
