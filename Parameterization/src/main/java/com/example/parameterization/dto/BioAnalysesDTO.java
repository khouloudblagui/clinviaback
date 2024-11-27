package com.example.parameterization.dto;

import com.example.parameterization.Enum.AnalysisType;
import com.example.parameterization.Enum.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BioAnalysesDTO {
    private String biologicalAnalysisName;
    private AnalysisType biologicalAnalysisType;
    private String biologicalAnalysisDesc;
    private MeasurementUnit biologicalAnalysisMeasurmentUnit;
    private Double biologicalAnalysisRefValueMin;
    private Double biologicalAnalysisRefValueMax;
}