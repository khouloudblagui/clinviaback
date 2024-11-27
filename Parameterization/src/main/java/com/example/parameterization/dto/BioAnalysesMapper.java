package com.example.parameterization.dto;


import com.example.parameterization.Entity.BioAnalyses;
import org.springframework.stereotype.Component;

@Component
public class BioAnalysesMapper {

    public BioAnalyses toEntity(BioAnalysesDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BioAnalysesDTO must not be null");
        }

        BioAnalyses bioAnalyses = new BioAnalyses();
        bioAnalyses.setBiologicalAnalysisName(dto.getBiologicalAnalysisName());
        bioAnalyses.setBiologicalAnalysisType(dto.getBiologicalAnalysisType());
        bioAnalyses.setBiologicalAnalysisDesc(dto.getBiologicalAnalysisDesc());
        bioAnalyses.setBiologicalAnalysisMeasurmentUnit(dto.getBiologicalAnalysisMeasurmentUnit());
        bioAnalyses.setBiologicalAnalysisRefValueMin(dto.getBiologicalAnalysisRefValueMin());
        bioAnalyses.setBiologicalAnalysisRefValueMax(dto.getBiologicalAnalysisRefValueMax());

        return bioAnalyses;
    }

    public BioAnalysesDTO toDTO(BioAnalyses entity) {
        if (entity == null) {
            throw new IllegalArgumentException("BioAnalyses must not be null");
        }

        BioAnalysesDTO dto = new BioAnalysesDTO();
        dto.setBiologicalAnalysisName(entity.getBiologicalAnalysisName());
        dto.setBiologicalAnalysisType(entity.getBiologicalAnalysisType());
        dto.setBiologicalAnalysisDesc(entity.getBiologicalAnalysisDesc());
        dto.setBiologicalAnalysisMeasurmentUnit(entity.getBiologicalAnalysisMeasurmentUnit());
        dto.setBiologicalAnalysisRefValueMin(entity.getBiologicalAnalysisRefValueMin());
        dto.setBiologicalAnalysisRefValueMax(entity.getBiologicalAnalysisRefValueMax());

        return dto;
    }
}