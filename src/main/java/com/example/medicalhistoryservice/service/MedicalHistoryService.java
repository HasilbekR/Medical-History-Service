package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.request.MedicalHistoryDto;
import com.example.medicalhistoryservice.domain.dto.request.DoctorDetailsForBooking;
import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.dto.response.Status;
import com.example.medicalhistoryservice.domain.dto.response.UserMedHistoryDto;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.domain.entity.MedicalHistoryEntity;
import com.example.medicalhistoryservice.exception.DataNotFoundException;
import com.example.medicalhistoryservice.repository.MedicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    public StandardResponse<MedicalHistoryEntity> save(MedicalHistoryDto medicalHistoryDto, Principal principal){
        UUID doctorId = userService.findUserIdByEmail(principal.getName());
        MedicalHistoryEntity medicalHistoryEntity = modelMapper.map(medicalHistoryDto, MedicalHistoryEntity.class);
        medicalHistoryEntity.setDoctorUuid(doctorId);
        return StandardResponse.<MedicalHistoryEntity>builder()
                .status(Status.SUCCESS)
                .message("Medical history successfully saved")
                .data(medicalHistoryRepository.save(medicalHistoryEntity))
                .build();
    }

    public StandardResponse<MedicalHistoryEntity> setTestResult(UUID historyId, DiagnosticTestResultEntity result){
        MedicalHistoryEntity historyEntity = medicalHistoryRepository.findById(historyId).orElseThrow(() -> new DataNotFoundException("History not found"));
        List<DiagnosticTestResultEntity> diagnosticTestResultEntities = historyEntity.getDiagnosticTestResultEntities();
        diagnosticTestResultEntities.add(result);
        historyEntity.setDiagnosticTestResultEntities(diagnosticTestResultEntities);
        return StandardResponse.<MedicalHistoryEntity>builder()
                .status(Status.SUCCESS)
                .message("Diagnostic test successfully saved")
                .data(medicalHistoryRepository.save(historyEntity))
                .build();
    }

    public StandardResponse<List<UserMedHistoryDto>> getPatientHistories(UUID patientId){
        List<UserMedHistoryDto> histories = new LinkedList<>();
        List<MedicalHistoryEntity> medicalHistoryEntities = medicalHistoryRepository.findMedicalHistoryEntitiesByPatientUuid(patientId).orElseThrow(() -> new DataNotFoundException("Data not found"));
        for (MedicalHistoryEntity medicalHistoryEntity : medicalHistoryEntities) {
            DoctorDetailsForBooking doctor = userService.findDoctorNameById(medicalHistoryEntity.getDoctorUuid());
            histories.add(UserMedHistoryDto.builder()
                    .id(medicalHistoryEntity.getId())
                    .doctorName(doctor.getFullName())
                    .specialty(doctor.getSpecialty())
                    .complaint(medicalHistoryEntity.getComplaint())
                    .date(medicalHistoryEntity.getCreatedDate().toLocalDate())
                    .build());
        }
        return StandardResponse.<List<UserMedHistoryDto>>builder().status(Status.SUCCESS).message("User's medical history data").data(histories).build();

    }
    public StandardResponse<List<MedicalHistoryEntity>> getDoctorReports(UUID doctorId){
        return StandardResponse.<List<MedicalHistoryEntity>>builder()
                .status(Status.SUCCESS)
                .message("Doctor's report")
                .data(medicalHistoryRepository.findMedicalHistoryEntitiesByDoctorUuid(doctorId).orElseThrow(() -> new DataNotFoundException("Data not found")))
                .build();
    }

}
