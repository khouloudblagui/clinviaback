package org.example.doctor.Controller;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.DTOs.AppointmentDTO;
import org.example.doctor.Entity.Appointment;
import org.example.doctor.Entity.Doctor;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Service.AppointmentService;
import org.example.doctor.Service.DoctorService;
import org.example.doctor.Service.PatientService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/doctor/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService  = patientService;
    }

    // Créer un nouveau rendez-vous
    @PostMapping("/")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        logger.info("Received request to create appointment: {}", appointmentDTO);

        // Rechercher le docteur par son ID utilisateur
        Optional<Doctor> optionalDoctor = doctorService.getDoctorByUserKy(appointmentDTO.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            logger.error("Doctor not found with userKy: {}", appointmentDTO.getDoctorId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Doctor doctor = optionalDoctor.get();

        // Rechercher le patient par son ID utilisateur
        Optional<Patient> optionalPatient = patientService.getPatientByUserKy(appointmentDTO.getPatientId());
        if (optionalPatient.isEmpty()) {
            logger.error("Patient not found with userKy: {}", appointmentDTO.getPatientId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Patient patient = optionalPatient.get();

        // Créer une nouvelle entité Appointment à partir des informations du DTO
        Appointment appointment = new Appointment();
        appointment.setName(appointmentDTO.getName());
        appointment.setEmail(appointmentDTO.getEmail());
        appointment.setGender(appointmentDTO.getGender());
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setMobile(appointmentDTO.getMobile());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setInjury(appointmentDTO.getInjury());

        logger.info("Creating appointment for patient: {} with doctor: {}", appointmentDTO.getPatientId(), appointmentDTO.getDoctorId());

        // Enregistrer le rendez-vous
        Appointment newAppointment = appointmentService.createAppointment(appointment);

        logger.info("Appointment created successfully with ID: {}", newAppointment.getId());

        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    // Récupérer un rendez-vous par son ID
    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long appointmentId) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(appointmentId));
        return appointment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Mettre à jour un rendez-vous existant
    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment updatedAppointment) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.updateAppointment(appointmentId, updatedAppointment));
        return appointment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Supprimer un rendez-vous
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Récupérer tous les rendez-vous
    @GetMapping("/")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Récupérer tous les rendez-vous d'un docteur
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Récupérer tous les rendez-vous d'un patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Récupérer tous les rendez-vous par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable AppointmentStatus status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}