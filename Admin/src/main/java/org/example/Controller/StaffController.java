package org.example.Controller;

import org.example.Entity.Staff;
import org.example.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/staffs")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Staff> getStaffById(@PathVariable Long id) {
        return staffRepository.findById(id);
    }

    @PostMapping
    public Staff createStaff(@RequestBody Staff staff) {
        return staffRepository.save(staff);
    }

    @PutMapping("/{id}")
    public Staff updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
        return staffRepository.findById(id).map(staff -> {
            staff.setImg(staffDetails.getImg());
            staff.setName(staffDetails.getName());
            staff.setEmail(staffDetails.getEmail());
            staff.setDate(staffDetails.getDate());
            staff.setAddress(staffDetails.getAddress());
            staff.setMobile(staffDetails.getMobile());
            staff.setDesignation(staffDetails.getDesignation());
            return staffRepository.save(staff);
        }).orElseThrow(() -> new RuntimeException("Staff not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return "Staff deleted with id " + id;
    }
}

