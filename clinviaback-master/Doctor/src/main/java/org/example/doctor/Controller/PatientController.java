package org.example.doctor.Controller;


import org.example.doctor.Entity.Patient;
import org.example.doctor.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/doctors/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Récupérer la liste de tous les patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Créer un nouveau patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    // Récupérer un patient par son ID utilisateur
    @GetMapping("/{userKy}")
    public ResponseEntity<Optional<Patient>> getPatientByUserKy(@PathVariable Integer userKy) {
        Optional<Patient> patient = patientService.getPatientByUserKy(userKy);
        return ResponseEntity.ok(patient);
    }

    // Mettre à jour un patient
    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId, @RequestBody Patient patient) {
        Optional<Patient> updatedPatient = patientService.updatePatient(patientId, patient);
        return updatedPatient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer un patient
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}