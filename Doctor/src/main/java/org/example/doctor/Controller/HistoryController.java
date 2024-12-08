package org.example.doctor.Controller;

import org.example.doctor.DTOs.HistoryDTO;
import org.example.doctor.DTOs.HistoryMapper;
import org.example.doctor.Entity.History;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Repository.PatientRepository;
import org.example.doctor.Service.HistoryService;
import org.example.doctor.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctor/histories")
@CrossOrigin(origins = "*")
public class HistoryController {
    private final HistoryService historyService;
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final HistoryMapper historyMapper;
    public HistoryController(HistoryService historyService, PatientService patientService, PatientRepository patientRepository, HistoryMapper historyMapper) {
        this.historyService = historyService;
        this.patientService = patientService;
        this.patientRepository = patientRepository;
        this.historyMapper = historyMapper;
    }

    @PostMapping("/")
    public ResponseEntity<History> createHistory(@RequestBody HistoryDTO historyDTO) {
        System.out.println("Received history: " + historyDTO);
        if (historyDTO.getPatientId() == null) {
            System.out.println("Error: Patient or Patient ID is missing");
            return ResponseEntity.badRequest().build(); // Vérifiez les données invalides
        }

        Patient patient =patientService.getPatientByUserKy(historyDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        History history = historyMapper.toEntity(historyDTO, patient);

        Optional<History> history1 = historyService.getHistoryByPatientId(patient.getId());

        history1.ifPresent(value -> history.setHis_ky(value.getHis_ky()));
        History savedHistory = historyService.saveOrUpdate(history);
        return ResponseEntity.ok(savedHistory);
    }



    @GetMapping("/{patientId}")
    public ResponseEntity<History> getHistoryByPatientId(@PathVariable Integer patientId) {
        Patient patient =patientService.getPatientByUserKy(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return historyService.getHistoryByPatientId(patient.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<History>> getAllHistories() {
        return ResponseEntity.ok(historyService.getAllHistories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Integer id) {
        historyService.deleteHistoryById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/stats")
    public Map<String, Long> getQuestionStats() {
        return historyService.getQuestionStats();
    }
}
