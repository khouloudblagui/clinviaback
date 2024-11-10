package org.example.doctor.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Integer user_Ky;      //
    private String firstname;
    private String lastname;
    private String email;
}