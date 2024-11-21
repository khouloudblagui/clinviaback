package org.example.doctor.Repository;

import org.example.doctor.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUserKy(Integer userKy);
} // Recherche du docteur par son ID d'utilisateur

