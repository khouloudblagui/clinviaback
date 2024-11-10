package org.example.doctor.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class AppointmentDTO {
    private String name;
    private String email;
    private String gender;
    private LocalDate date;
    private String time;
    private String mobile;
    private Integer doctorId; // Utilisez l'ID du docteur
    private Integer patientId; // Utilisez l'ID du patient
    private String injury;
}
