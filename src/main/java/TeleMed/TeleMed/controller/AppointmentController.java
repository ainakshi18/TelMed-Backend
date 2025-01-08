package TeleMed.TeleMed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TeleMed.TeleMed.Models.Appointment;
import TeleMed.TeleMed.auth.JwtUtil;
import TeleMed.TeleMed.service.AppointmentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private JwtUtil jwtUtil ;

    // Create a new appointment

@PostMapping()
@PreAuthorize("hasRole('ROLE_PATIENT')")
public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment,@RequestHeader("Authorization") String token) {
    token = token.startsWith("Bearer ") ? token.substring(7) : token;

    // Extract the userId from JWT token
    String userId = jwtUtil.extractUserId(token);
    System.out.println("----------------this is userId--------" + userId);  // Log the extracted userId

    // Set the extracted patientId to the appointment
    appointment.setPatientId(userId);

    // Create the appointment
    Appointment createdAppointment = appointmentService.createAppointment(appointment);

    // Update patient and doctor appointments if necessary
    appointmentService.updatePatientAndDoctorAppointments(createdAppointment);

    // Return the created appointment
    return ResponseEntity.ok(createdAppointment);
}

    // Get all appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Get an appointment by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PATIENT')or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable String patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(appointments);
    }

    // Get appointments by Doctor ID
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorId(@PathVariable String doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return appointments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(appointments);
    }
    // Update the status of an appointment
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String id, @RequestBody Appointment updatedAppointment) {
        Appointment updated = appointmentService.updateAppointment(id, updatedAppointment);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Appointment not found
        }
    }

}
