package org.example.doctor.DTOs;

import org.example.doctor.DTOs.HistoryDTO;
import org.example.doctor.Entity.History;
import org.example.doctor.Entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {

    public History toEntity(HistoryDTO historyDTO, Patient patient) {
        if (historyDTO == null || patient == null) {
            throw new IllegalArgumentException("HistoryDTO and Patient must not be null");
        }

        History history = new History();
        history.setPatient(patient);
        history.setQ1(historyDTO.getQ1());
        history.setQ2(historyDTO.getQ2());
        history.setQ3(historyDTO.getQ3());
        history.setQ4(historyDTO.getQ4());
        history.setQ5(historyDTO.getQ5());
        history.setQ6(historyDTO.getQ6());
        history.setQ7(historyDTO.getQ7());
        history.setMed_details(historyDTO.getMed_details());
        history.setQ8(historyDTO.getQ8());
        history.setQ9(historyDTO.getQ9());
        history.setQ10(historyDTO.getQ10());
        history.setQ11(historyDTO.getQ11());
        history.setAll_details(historyDTO.getAll_details());
        history.setQ12(historyDTO.getQ12());
        history.setSur_details(historyDTO.getSur_details());
        history.setQ13(historyDTO.getQ13());
        history.setQ14(historyDTO.getQ14());
        history.setQ15(historyDTO.getQ15());

        return history;
    }
}

