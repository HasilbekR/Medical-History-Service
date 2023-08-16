package com.example.medicalhistoryservice.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagnosticTestResult extends BaseEntity{
    private UUID patientId;
    private String result;
}
