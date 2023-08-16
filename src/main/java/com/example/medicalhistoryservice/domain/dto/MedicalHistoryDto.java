package com.example.medicalhistoryservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalHistoryDto {
    @NotBlank(message = "Doctor id must not be blank")
    private UUID doctorUuid;
    @NotBlank(message = "Patient id must not be blank")
    private UUID patientUuid;
    @NotBlank(message = "Patient's complaint must not be blank")
    private String complaint;
    @NotBlank(message = "Diagnosis must not be blank")
    private String diagnosis;
    @NotBlank(message = "Diagnosis tests must not be blank")
    private String diagnosisTests;
    @NotBlank(message = "Treatment plan must not be blank")
    private String treatmentPlan;
}
