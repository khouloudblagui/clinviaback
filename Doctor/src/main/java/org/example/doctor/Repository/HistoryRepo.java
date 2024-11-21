package org.example.doctor.Repository;

import org.example.doctor.Entity.History;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryRepo extends JpaRepository<History, Integer> {
    /**
     * Trouver un historique en fonction d'un patient.
     *
     * @param patientId le patient associé à l'historique
     * @return l'historique du patient
     */
    Optional<History> findByPatientId(Long patientId);

    /**
     * Vérifier si un historique existe pour un patient donné.
     *
     * @param patientId le patient associé
     * @return true si un historique existe, sinon false
     */
    boolean existsByPatientId(Long patientId);
}

