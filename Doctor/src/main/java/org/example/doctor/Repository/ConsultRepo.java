package org.example.doctor.Repository;

import org.example.doctor.Entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsultRepo extends JpaRepository<Consultation,Integer> {
    List<Consultation> findByUserKy(Integer userKy);
}
