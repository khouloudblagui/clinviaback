package org.example.authentification.proxy;

import org.example.authentification.User.Doctor;
import org.example.authentification.User.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;



@FeignClient(name = "doctor-service")
public interface PatientClient {



    @GetMapping("/api/v1/doctors/patients")
    List<Patient> getAllPatients();

    @PostMapping("/api/v1/doctors/patients")
    Patient createPatient(@RequestBody Patient patient);

    @PostMapping("/api/v1/doctors")
    ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) ;

}
