package com.example.medicalhistoryservice.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class UserDiagnosticTestDto {
    private UUID id;
    private LocalDate date;
    private String diagnosticTest;
    private String hospitalName;
}
