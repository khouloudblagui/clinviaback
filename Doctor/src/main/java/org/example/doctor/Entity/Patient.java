package org.example.doctor.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data
@Table(name = "patients") // Utilisation du pluriel pour la table
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;

    // Référence à l'ID de l'utilisateur dans le service d'authentification
    @Column(name = "user_ky", nullable = false)
    private Integer userKy;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "blood_group")
    private String bloodGroup; // Groupe sanguin du patient

    @Column(name = "date_of_birth") // Date de naissance
    private String dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "treatment")
    private String treatment;
    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();
}
