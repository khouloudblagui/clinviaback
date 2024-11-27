package org.example.doctor.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
    private Integer patientId;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;
    private String q7;
    private String med_details;
    private String q8;
    private String q9;
    private String q10;
    private String q11;
    private String all_details;
    private String q12;
    private String sur_details;
    private String q13;
    private String q14;
    private String q15;
}
