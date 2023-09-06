package com.example.medicalhistoryservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "medical_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MedicalHistoryEntity extends BaseEntity{
    private UUID doctorUuid;
    private UUID patientUuid;

    @Column(columnDefinition = "text")
    private String complaint;

    @Column(columnDefinition = "text")
    private String diagnosis;

    @Column(columnDefinition = "text")
    private String diagnosisTests;

    @Column(columnDefinition = "text")
    private String treatmentPlan;
    @OneToMany
    private List<DiagnosticTestResultEntity> diagnosticTestResultEntities;
}
