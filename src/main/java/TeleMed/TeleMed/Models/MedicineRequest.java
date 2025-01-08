package TeleMed.TeleMed.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicine_requests") // MongoDB collection name
public class MedicineRequest {

    @Id
    private String id;

    private String patientId;  // The ID of the patient making the request
    private String medicineId; // The ID of the requested medicine
    private String storeId;    // The ID of the store fulfilling the request
    private int quantity;      // Quantity of the medicine requested
    private String status;     // Status of the request (Pending, Approved, Fulfilled, etc.)

    private String requestDate; // Date when the request was created
    private String fulfilmentDate; // Date when the request is expected to be fulfilled (if applicable)

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getFulfilmentDate() {
        return fulfilmentDate;
    }

    public void setFulfilmentDate(String fulfilmentDate) {
        this.fulfilmentDate = fulfilmentDate;
    }

    @Override
    public String toString() {
        return "MedicineRequest{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", medicineId='" + medicineId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", requestDate='" + requestDate + '\'' +
                ", fulfilmentDate='" + fulfilmentDate + '\'' +
                '}';
    }


}
