package org.example.doctor.Service;



import org.example.doctor.DTOs.UserResponseDTO;
import org.example.doctor.Entity.Patient;

import org.example.doctor.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Récupérer la liste de tous les patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Récupérer un patient par son ID utilisateur
    public Optional<Patient> getPatientByUserKy(Integer userKy) {
        return patientRepository.findByUserKy(userKy);
    }

    public Optional<Patient> getPatientByID(Long id) {
        return patientRepository.findById(id);
    }

    // Créer un nouveau patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Mettre à jour un patient
    public Optional<Patient> updatePatient(Long id, Patient updatedPatient) {
        return patientRepository.findById(id).map(patient -> {
            patient.setMobile(updatedPatient.getMobile());
            patient.setGender(updatedPatient.getGender());
            patient.setBloodGroup(updatedPatient.getBloodGroup());
            patient.setDateOfBirth(updatedPatient.getDateOfBirth());
            patient.setAddress(updatedPatient.getAddress());
            patient.setTreatment(updatedPatient.getTreatment());
            return patientRepository.save(patient);
        });
    }

    // Supprimer un patient
    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }
}