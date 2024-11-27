package org.example.doctor.Controller;

import org.example.doctor.DTOs.ConsultationDTO;
import org.example.doctor.Entity.Consultation;
import org.example.doctor.Service.ConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    @Autowired
    private ConsultService Conser;

    //add
    @PostMapping(value = "/add-consultation")
    public ResponseEntity<Consultation> addcon(@RequestBody Consultation Con) {
        Conser.saveorUpdate(Con);
        return ResponseEntity.ok(Con);
    }

    //GetAll
    @GetMapping("/all-consultation")
    public List<Consultation> getConsultation() {
        return Conser.getConsultations();
    }

    @GetMapping(value = "/user-consultation/{userKy}")
    public ResponseEntity<List<Consultation>> getConsultationsByUserKy(@PathVariable Integer userKy) {
        List<Consultation> consultations = Conser.getConsultationsByUserKy(userKy);
        if (consultations != null && !consultations.isEmpty()) {
            return ResponseEntity.ok(consultations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/view-consultation/{id}")
    public ConsultationDTO getConsultationDetails(@PathVariable Integer id) {
        return Conser.getConsultationDetails(id);
    }
}
