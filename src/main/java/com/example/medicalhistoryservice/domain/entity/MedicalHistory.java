package com.example.medicalhistoryservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "medical-history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MedicalHistory extends BaseEntity{
    private UUID doctorId;
    private UUID patientId;
    private String complaint;
    private String diagnosis;
    private String diagnosisTests;
    private String treatmentPlan;
    @OneToMany
    private List<DiagnosticTestResult> diagnosticTestResults;
}
