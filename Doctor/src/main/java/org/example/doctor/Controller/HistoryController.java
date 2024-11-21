package org.example.doctor.Controller;

import org.example.doctor.Entity.History;
import org.example.doctor.Entity.Patient;
import org.example.doctor.Service.HistoryService;
import org.example.doctor.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctor/histories")
@CrossOrigin(origins = "*")
public class HistoryController {
    private final HistoryService historyService;
    private final PatientService patientService;

    public HistoryController(HistoryService historyService, PatientService patientService) {
        this.historyService = historyService;
        this.patientService = patientService;
    }

    @PostMapping("/")
    public ResponseEntity<History> createHistory(@RequestBody History history) {
        System.out.println("Received history: " + history);
        if (history.getPatient() == null || history.getPatient().getId() == null) {
            System.out.println("Error: Patient or Patient ID is missing");
            return ResponseEntity.badRequest().build(); // Vérifiez les données invalides
        }

        Long patientId = history.getPatient().getId();
        if (historyService.historyExistsForPatient(patientId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Statut 409 si l'historique existe déjà
        }

        // Appelez la méthode sur l'instance injectée de PatientService
        Optional<Patient> patientOptional = patientService.getPatientByID(patientId);
        if (patientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Statut 404 si le patient est introuvable
        }

        history.setPatient(patientOptional.get()); // Associez l'entité Patient récupérée
        History savedHistory = historyService.saveOrUpdate(history);
        return ResponseEntity.ok(savedHistory);
    }



    @GetMapping("/{patientId}")
    public ResponseEntity<History> getHistoryByPatientId(@PathVariable Long patientId) {
        return historyService.getHistoryByPatientId(patientId)
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
}
