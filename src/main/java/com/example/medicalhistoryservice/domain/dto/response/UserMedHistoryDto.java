package com.example.medicalhistoryservice.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
public class UserMedHistoryDto {
    private UUID id;
    private String doctorName;
    private String specialty;
    private String complaint;
    private LocalDate date;

}
