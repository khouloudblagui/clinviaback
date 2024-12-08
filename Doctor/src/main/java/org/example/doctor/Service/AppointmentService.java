package org.example.doctor.Service;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.Entity.Appointment;
import org.example.doctor.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("doctorAppointmentService")
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Créer un nouveau rendez-vous
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Trouver un rendez-vous par son ID
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + appointmentId));
    }

    // Mettre à jour un rendez-vous existant
    public Appointment updateAppointment(Long appointmentId, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointmentById(appointmentId);
        existingAppointment.setDate(updatedAppointment.getDate());
        existingAppointment.setTime(updatedAppointment.getTime());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setStatus(updatedAppointment.getStatus());
        return appointmentRepository.save(existingAppointment);
    }

    // Supprimer un rendez-vous par son ID
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    // Récupérer tous les rendez-vous
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }


    // Récupérer tous les rendez-vous d'un docteur
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Récupérer tous les rendez-vous d'un patient
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Récupérer tous les rendez-vous avec un statut donné
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }


    public Map<String, Long> getAppointmentsLastSixMonths() {
        Map<String, Long> appointmentCounts = new HashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 5; i >= 0; i--) {
            YearMonth yearMonth = YearMonth.from(today.minusMonths(i));
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();

            Long count = appointmentRepository.countByDateBetween(startDate, endDate);
            appointmentCounts.put(yearMonth.toString(), count);
        }

        return appointmentCounts;
    }


    public List<Appointment> getAppointmentsByDoctorUserKy(Integer userKy) {
        return appointmentRepository.findAllByDoctorUserKy(userKy);
    }


    public Map<AppointmentStatus, Long> getAppointmentCountByStatus() {
        // Initialiser le HashMap avec tous les statuts à 0
        Map<AppointmentStatus, Long> appointmentCountMap = new HashMap<>();
        for (AppointmentStatus status : AppointmentStatus.values()) {
            appointmentCountMap.put(status, 0L);
        }

        // Récupérer les données de la base et mettre à jour les valeurs
        List<Object[]> results = appointmentRepository.countAppointmentsByStatus();
        for (Object[] result : results) {
            AppointmentStatus status = (AppointmentStatus) result[0];
            Long count = (Long) result[1];
            appointmentCountMap.put(status, count);
        }

        return appointmentCountMap;
    }
}
