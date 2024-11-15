package org.example.doctor.Service;


import org.example.doctor.DTOs.UserResponseDTO;
import org.example.doctor.Entity.Doctor;
import org.example.doctor.Feign.UserClient;
import org.example.doctor.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserClient userClient;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, UserClient userClient) {
        this.doctorRepository = doctorRepository;
        this.userClient = userClient;
    }



    // Récupérer la liste de tous les docteurs
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Récupérer un docteur par ID utilisateur
    public Optional<Doctor> getDoctorByUserKy(Integer userKy) {
        return doctorRepository.findByUserKy(userKy);
    }

    public Optional<Doctor> getDoctorid(Long id) {
        return doctorRepository.findById(id);
    }

    // Récupérer les informations utilisateur via Feign client
    public UserResponseDTO getUserInfoByUserKy(Integer userKy) {
        return userClient.getUserByKy(userKy);
    }

    // Créer un nouveau docteur
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Mettre à jour un docteur
    public Optional<Doctor> updateDoctor(Long id, Doctor updatedDoctor) {
        return doctorRepository.findById(id).map(doctor -> {
            doctor.setDepartment(updatedDoctor.getDepartment());
            doctor.setSpecialization(updatedDoctor.getSpecialization());
            doctor.setDegree(updatedDoctor.getDegree());
            doctor.setMobile(updatedDoctor.getMobile());
            doctor.setDateOfJoining(updatedDoctor.getDateOfJoining());
            return doctorRepository.save(doctor);
        });
    }

    // Supprimer un docteur
    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}

 /*   private final DoctorRepository doctorRepository;
    private final UserClient userClient;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, UserClient userClient) {
        this.doctorRepository = doctorRepository;
        this.userClient = userClient;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public DoctorDTO getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + doctorId));

        // Utiliser Feign pour obtenir les détails de l'utilisateur à partir du microservice Authentification
        UserDto user = userClient.getUserById(doctor.getUserId());

        // Créer et retourner un DoctorDTO en combinant les informations du User et du Doctor
        return new DoctorDTO(user, doctor);
    }
    // Méthode pour obtenir un Doctor directement
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + doctorId));
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long doctorId, Doctor doctorDetails) {
        Doctor existingDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + doctorId));

        // Mise à jour des champs spécifiques au docteur
        existingDoctor.setSpecialization(doctorDetails.getSpecialization());
        existingDoctor.setDepartment(doctorDetails.getDepartment());
        existingDoctor.setDegree(doctorDetails.getDegree());
        existingDoctor.setDateOfJoining(doctorDetails.getDateOfJoining());

        return doctorRepository.save(existingDoctor);
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    public Doctor getDoctorByName(String name) {
        return doctorRepository.findByName(name);
    }
}







package org.example.doctor.Service;

import org.example.doctor.Entity.Doctor;
import org.example.doctor.Exception.ResourceNotFoundException;
import org.example.doctor.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    // Mettre à jour un docteur existant
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + id));

        // Mise à jour des champs
        existingDoctor.setName(doctorDetails.getName());
        existingDoctor.setEmail(doctorDetails.getEmail());
        existingDoctor.setSpecialization(doctorDetails.getSpecialization());
        existingDoctor.setMobile(doctorDetails.getMobile());
        existingDoctor.setDepartment(doctorDetails.getDepartment());
        existingDoctor.setDegree(doctorDetails.getDegree());
        existingDoctor.setDateOfJoining(doctorDetails.getDateOfJoining());

        return doctorRepository.save(existingDoctor);
    }
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctorByName(String name) {
        return doctorRepository.findByName(name);
    }
}*/


