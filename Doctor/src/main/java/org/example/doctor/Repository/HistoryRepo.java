package org.example.doctor.Repository;

import org.example.doctor.Entity.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT COUNT(h) FROM History h WHERE h.q1 = 'Yes'")
    Long countQ1Yes();

    @Query("SELECT COUNT(h) FROM History h WHERE h.q2 = 'Yes'")
    Long countQ2Yes();

    @Query("SELECT COUNT(h) FROM History h WHERE h.q3 = 'Yes'")
    Long countQ3Yes();
}

