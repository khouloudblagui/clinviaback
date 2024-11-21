package org.example.doctor.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false)
    private Long id;
    @Column(name = "user_ky", nullable = false)
    private Integer userKy; // Référence à l'ID de l'utilisateur dans le service d'authentification
    @Column(name = "mobile")
    private String mobile;


    private String name ;

    @Column(name = "specialty")
    private String specialization; // Spécialité du docteur

    @Column(name = "department")
    private String department; // Département auquel le docteur est associé

    @Column(name = "degree")
    private String degree; // Diplôme du docteur

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_joining")
    private Date dateOfJoining; // Date à laquelle le docteur a rejoint l'hôpital
    @JsonIgnore
    @OneToMany
    private List<Appointment> appointments ;
}


/*package org.example.doctor.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name; // Correspond à `name` côté frontend

    @Column(name = "email")
    private String email;

    @Column(name = "specialty")
    private String specialization; // Correspond à `specialization` côté frontend

    @Column(name = "phone")
    private String mobile; // Correspond à `mobile` côté frontend

    @Column(name = "department")
    private String department;

    @Column(name = "degree")
    private String degree;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_joining")
    private Date dateOfJoining; // Correspond à `date` côté frontend

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();
}*/
