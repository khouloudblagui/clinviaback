package org.example.doctor.Repository;

import org.example.doctor.Entity.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareRepo extends JpaRepository<CarePlan,Integer> {
    List<CarePlan> findByUserKy(Integer userKy);
}
