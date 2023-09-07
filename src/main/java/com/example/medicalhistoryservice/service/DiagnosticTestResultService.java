package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.dto.response.Status;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.exception.DataNotFoundException;
import com.example.medicalhistoryservice.repository.DiagnosticTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiagnosticTestResultService {
    private final DiagnosticTestResultRepository diagnosticTestResultRepository;
    private final ModelMapper modelMapper;
    public StandardResponse<DiagnosticTestResultEntity> save(DiagnosticTestResultEntity diagnosticTestResultEntity){
        return StandardResponse.<DiagnosticTestResultEntity>builder()
                .status(Status.SUCCESS)
                .message("Diagnostic test successfully saved")
                .data(diagnosticTestResultRepository.save(diagnosticTestResultEntity))
                .build();
    }
    public StandardResponse<List<DiagnosticTestResultEntity>> getPatientTestResults(UUID patientId){
        return StandardResponse.<List<DiagnosticTestResultEntity>>builder()
                .status(Status.SUCCESS)
                .message("Patient's diagnostic results")
                .data(diagnosticTestResultRepository.findDiagnosticTestResultEntitiesByPatientId(patientId).orElseThrow(()-> new DataNotFoundException("Patient not found")))
                .build();
    }
    public StandardResponse<DiagnosticTestResultEntity> update(UUID resultId, DiagnosticTestResultEntity diagnosticTestResultEntity){
        DiagnosticTestResultEntity result = diagnosticTestResultRepository.findById(resultId).orElseThrow(() -> new DataNotFoundException("Test result not found"));
        modelMapper.map(diagnosticTestResultEntity,result);
        return StandardResponse.<DiagnosticTestResultEntity>builder()
                .status(Status.SUCCESS)
                .message("Diagnostic test result updated")
                .data(diagnosticTestResultRepository.save(result))
                .build();
    }
}
