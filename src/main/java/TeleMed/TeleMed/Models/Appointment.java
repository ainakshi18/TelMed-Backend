package TeleMed.TeleMed.Models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appointments")
public class Appointment {

    private String id;
    private String name;
    private String phoneNumber;
    private String age;
    private String sex;
    private List<String> Symptoms;
    private String doctorId;
    private String patientId;
	private String status;  // for completed or follow-up appointments
    private String Consultation;
private String time;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<String> getSymptoms() {
		return Symptoms;
	}

	public void setSymptoms(List<String> symptoms) {
		Symptoms = symptoms;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getConsultation() {
		return Consultation;
	}

	public void setConsultation(String consultation) {
		Consultation = consultation;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


}
