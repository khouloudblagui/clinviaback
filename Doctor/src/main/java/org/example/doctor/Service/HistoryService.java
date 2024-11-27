package org.example.doctor.Service;

import lombok.RequiredArgsConstructor;
import org.example.doctor.Entity.History;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Repository.HistoryRepo;
import org.example.doctor.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HistoryService {
    private final HistoryRepo historyRepo;

    public HistoryService(HistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }

    public History saveOrUpdate(History history) {
        return historyRepo.save(history);
    }

    public boolean historyExistsForPatient(Long patientId) {
        return historyRepo.existsByPatientId(patientId);
    }

    public Optional<History> getHistoryByPatientId(Long patientId) {
        return historyRepo.findByPatientId(patientId);
    }

    public List<History> getAllHistories() {
        return historyRepo.findAll();
    }

    public void deleteHistoryById(Integer id) {
        historyRepo.deleteById(id);
    }
}