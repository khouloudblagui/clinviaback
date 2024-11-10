package org.example.doctor.Controller;

import org.example.doctor.DTOs.DoctorDTO;
import org.example.doctor.Entity.Doctor;
import org.example.doctor.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Récupérer la liste de tous les docteurs
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // Créer un nouveau docteur
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.ok(createdDoctor);
    }

    // Récupérer un docteur par son ID utilisateur
    @GetMapping("/{userKy}")
    public ResponseEntity<Optional<Doctor>> getDoctorByUserKy(@PathVariable Integer userKy) {
        Optional<Doctor> doctor = doctorService.getDoctorByUserKy(userKy);
        return ResponseEntity.ok(doctor);
    }

    // Mettre à jour un docteur
    @PutMapping("/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor doctor) {
        Optional<Doctor> updatedDoctor = doctorService.updateDoctor(doctorId, doctor);
        return updatedDoctor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer un docteur
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}