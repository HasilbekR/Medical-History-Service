package com.example.medicalhistoryservice.domain.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@Entity(name = "physical_signs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhysicalSignsEntity extends BaseEntity{
    private UUID patientId;
    private int bloodGroup;
    private String allergicReactionsTo;
    private String bloodPressure;
}
