package org.example.doctor.Controller;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.DTOs.AppointmentDTO;
import org.example.doctor.DTOs.UserResponseDTO;
import org.example.doctor.Entity.Appointment;
import org.example.doctor.Entity.Doctor;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Feign.UserClient;
import org.example.doctor.Service.AppointmentService;
import org.example.doctor.Service.DoctorService;
import org.example.doctor.Service.PatientService;
import org.example.doctor.config.MailConfig;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
    private final UserClient userClient ;
    private final MailConfig emailService;
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, UserClient userClient, MailConfig emailService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService  = patientService;
        this.userClient = userClient;
        this.emailService = emailService;
    }

    // Créer un nouveau rendez-vous
    @PostMapping("/")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        logger.info("Received request to create appointment: {}", appointmentDTO);

        // Rechercher le docteur par son ID utilisateur
 /*       Optional<Doctor> optionalDoctor = doctorService.getDoctorByUserKy(appointmentDTO.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            logger.error("Doctor not found with userKy: {}", appointmentDTO.getDoctorId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Doctor doctor = optionalDoctor.get();*/

        // Rechercher le patient par son ID utilisateur
        Optional<Patient> optionalPatient = patientService.getPatientByID(Long.valueOf(appointmentDTO.getPatientId()));
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
      // appointment.setDoctor(null);
        appointment.setPatient(patient);
        appointment.setInjury(appointmentDTO.getInjury());
        appointment.setStatus(AppointmentStatus.PENDING);

       // logger.info("Creating appointment for patient: {} with doctor: {}", appointmentDTO.getPatientId(), appointmentDTO.getDoctorId());

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

    @GetMapping("affectdoctor/{appointmentId}/{idDoctor}")
    public ResponseEntity<Appointment> affectdoctor(@PathVariable Long appointmentId , @PathVariable Long idDoctor) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(appointmentId));
        Optional<Doctor> doctor = doctorService.getDoctorid(idDoctor);
        appointment.get().setDoctor(doctor.get());
        return ResponseEntity.ok(appointmentService.createAppointment(appointment.get()));
    }

    @GetMapping("cancel/{appointmentId}")
    public ResponseEntity<Appointment> cancelAppointmentById(@PathVariable Long appointmentId) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(appointmentId));
        appointment.get().setStatus(AppointmentStatus.CANCELLED);
        return ResponseEntity.ok(appointmentService.createAppointment(appointment.get()));
    }

    @GetMapping("accept/{appointmentId}")
    public ResponseEntity<Appointment> acceptAppointmentById(@PathVariable Long appointmentId) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(appointmentId));
        appointment.get().setStatus(AppointmentStatus.CONFIRMED);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("hrportal2023@gmail.com");
        simpleMailMessage.setTo(appointment.get().getEmail());
        simpleMailMessage.setSubject("Appointment Confirmation");
        simpleMailMessage.setText("We are pleased to inform you that your appointment has been accepted.");
        try {
            emailService.sendEmail(simpleMailMessage);
            return ResponseEntity.ok(appointmentService.createAppointment(appointment.get()));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;

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