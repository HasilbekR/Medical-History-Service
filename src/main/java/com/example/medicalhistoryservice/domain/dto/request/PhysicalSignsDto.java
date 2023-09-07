package com.example.medicalhistoryservice.domain.dto.request;

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
public class PhysicalSignsDto {
    @NotBlank(message = "Patient id must not be blank")
    private UUID patientId;
    private int bloodGroup;
    private String allergicReactionsTo;
    private String bloodPressure;
}
