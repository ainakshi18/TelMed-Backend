package TeleMed.TeleMed.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "medicalStores")
public class MedicalStore {
    @Id
    private String id;
	private String name;
    private Address address;
    private String phone;
    private String email;
    private List<Medicine> availableMedicines; // List of medicines with quantities
    private List<String> prescriptions; // List of Prescription IDs
private String ImageUrl;
private List<String> request; 

private List<String> order; 
    // Getters and Setters
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Medicine> getAvailableMedicines() {
		return availableMedicines;
	}

	public void setAvailableMedicines(List<Medicine> availableMedicines) {
		this.availableMedicines = availableMedicines;
	}

	public List<String> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<String> prescriptions) {
		this.prescriptions = prescriptions;
	}


	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public List<String> getRequest() {
		return request;
	}

	public void setRequest(List<String> request) {
		this.request = request;
	}

	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		this.order = order;
	}




	public class Address {
	    private String street;
	    private String city;
	    private String state;
	    private String zipCode;
	    
	    // Getters and Setters
	    public String getStreet() { return street; }
	    public void setStreet(String street) { this.street = street; }
	    
	    public String getCity() { return city; }
	    public void setCity(String city) { this.city = city; }
	    
	    public String getState() { return state; }
	    public void setState(String state) { this.state = state; }
	    
	    public String getZipCode() { return zipCode; }
	    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
	}


}

