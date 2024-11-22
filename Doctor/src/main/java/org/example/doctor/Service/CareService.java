package org.example.doctor.Service;

import lombok.RequiredArgsConstructor;
import org.example.doctor.Entity.CarePlan;
import org.example.doctor.Repository.CareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CareService {

    @Autowired
    private final CareRepo careRepo;

    public void saveorUpdate(CarePlan care) {

        careRepo.save(care);
    }

    public List<CarePlan> getCareplans() {
        return careRepo.findAll();
    }

    public void delete(Integer care_ky) {

        careRepo.deleteById(care_ky);
    }
    public List<CarePlan> getCareplansByUserKy(Integer userKy) {
        return careRepo.findByUserKy(userKy);
    }
}