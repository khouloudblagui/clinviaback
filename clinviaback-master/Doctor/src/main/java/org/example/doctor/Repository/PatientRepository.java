package org.example.doctor.Repository;

import org.example.doctor.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Recherche d'un patient par l'ID utilisateur (userKy)
    Optional<Patient> findByUserKy(Integer userKy);
}
