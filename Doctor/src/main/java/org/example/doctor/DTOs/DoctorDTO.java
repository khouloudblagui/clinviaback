package org.example.doctor.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String specialization;

    private String department;

    private String degree;

    private Date dateOfJoining;

    // Constructeur qui combine les informations de UserDTO et Doctor

}
