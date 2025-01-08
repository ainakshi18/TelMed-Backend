package TeleMed.TeleMed.Models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "patients")
public class Patient {
    @Id
    private String id;
	private String name;
    private int age;
    private String gender;
    private Address address;
    private String phone;
    private String email;
    private List<String> medicalHistory; // List of Consultation IDs
    private List<String> Consultation;
    private String currentConsultationId;
    private String role;
    private List<String> request;
    private List<String> order;
    private List<String> appointments;
    public List<String> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<String> appointments) {
		this.appointments = appointments;
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public List<String> getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(List<String> medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getCurrentConsultationId() {
		return currentConsultationId;
	}

	public void setCurrentConsultationId(String currentConsultationId) {
		this.currentConsultationId = currentConsultationId;
	}



    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getConsultation() {
		return Consultation;
	}

	public void setConsultation(List<String> consultation) {
		Consultation = consultation;
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



	public static class Address {
		private String city;
        private String state;
        private String pincode;

        // Getters and Setters
        public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getPincode() {
			return pincode;
		}
		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

    }
}