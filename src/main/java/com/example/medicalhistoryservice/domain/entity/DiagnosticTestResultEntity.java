package com.example.medicalhistoryservice.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;
@Entity(name = "test-results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnosticTestResultEntity extends BaseEntity{
    private UUID patientId;
    private String result;
}
