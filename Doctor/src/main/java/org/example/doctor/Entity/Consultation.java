package org.example.doctor.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "consultation")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer con_ky;

    private String doctorName;

    private String descCon;

    private String descSur;
    private String descPre;

    private String comment;


    private String  medication ;
    private String  analyses ;
    private String  surgical ;
    private String  vaccination ;

    @Column(name="user_ky")
    private Integer userKy;

    //med
    @ElementCollection
    private List<Integer> medicationIds = new ArrayList<>(); // List of Medication IDs



}
