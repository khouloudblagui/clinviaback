package org.example.authentification.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private Long id;

    private Integer userKy;

    private String mobile;

    private String gender;

    private String bloodGroup; // Groupe sanguin du patient

    private String dateOfBirth;

    private String address;

    private String treatment;
}
