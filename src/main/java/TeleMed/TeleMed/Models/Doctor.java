package TeleMed.TeleMed.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "doctors")
public class Doctor {
    @Id
    private String id;
	private String name;
    private String specialization;
    private String phone;
    private String email;
    private List<String> availability; // List of available time slots
    private List<String> consultations; // List of Consultation IDs
    private List<String> appointments;
    private String imageUrl;
private String rating;

private String qualifications;
private String hospitalName;
private String gender;
private String signUrl;

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
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
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
	public List<String> getAvailability() {
		return availability;
	}
	public void setAvailability(List<String> availability) {
		this.availability = availability;
	}
	public List<String> getConsultations() {
		return consultations;
	}
	public void setConsultations(List<String> consultations) {
		this.consultations = consultations;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getQualifications() {
		return qualifications;
	}
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<String> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<String> appointments) {
		this.appointments = appointments;
	}
	public String getSignUrl() {
		return signUrl;
	}
	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

}