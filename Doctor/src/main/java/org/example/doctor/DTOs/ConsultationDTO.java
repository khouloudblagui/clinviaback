package org.example.doctor.DTOs;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultationDTO {
    private Integer con_ky;
    private String doctorName;
    private String descCon;
    private String descSur;
    private String comment;
    private Integer userKy;



    // Getters and Setters
}