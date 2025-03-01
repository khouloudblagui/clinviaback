package org.example.Controller;

import org.example.DTO.AppointmentDTO;
import org.example.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/appointments")
public class AdminAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.getAppointmentDetails(appointmentId));
    }
}

