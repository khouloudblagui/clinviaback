package org.example.doctor.Repository;

import org.example.Enum.AppointmentStatus;
import org.example.doctor.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStatus(AppointmentStatus status);
    @Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
    List<Object[]> countAppointmentsByStatus();

    Long countByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.userKy = :userKy")
    List<Appointment> findAllByDoctorUserKy(@Param("userKy") Integer userKy);
}
