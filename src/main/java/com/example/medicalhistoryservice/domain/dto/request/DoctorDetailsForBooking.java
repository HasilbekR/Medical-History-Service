package com.example.medicalhistoryservice.domain.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDetailsForBooking {
    private UUID id;
    private String fullName;
    private String roomNumber;
    private String specialty;
    private UUID hospitalId;
}
