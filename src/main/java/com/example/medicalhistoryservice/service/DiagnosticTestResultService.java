package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.dto.response.Status;
import com.example.medicalhistoryservice.domain.dto.response.UserDiagnosticTestDto;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.exception.DataNotFoundException;
import com.example.medicalhistoryservice.repository.DiagnosticTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiagnosticTestResultService {
    private final DiagnosticTestResultRepository diagnosticTestResultRepository;
    private final ModelMapper modelMapper;
    private final DataExchangeService dataExchangeService;
    public StandardResponse<DiagnosticTestResultEntity> save(DiagnosticTestResultEntity diagnosticTestResultEntity, Principal principal){
        UUID hospitalId = dataExchangeService.findHospitalId(principal.getName());
        String hospitalName = dataExchangeService.findHospitalName(hospitalId);
        diagnosticTestResultEntity.setHospitalName(hospitalName);
        return StandardResponse.<DiagnosticTestResultEntity>builder()
                .status(Status.SUCCESS)
                .message("Diagnostic test successfully saved")
                .data(diagnosticTestResultRepository.save(diagnosticTestResultEntity))
                .build();
    }
    public StandardResponse<List<UserDiagnosticTestDto>> getPatientTestResults(UUID patientId){
        List<DiagnosticTestResultEntity> testResults = diagnosticTestResultRepository.findDiagnosticTestResultEntitiesByPatientId(patientId).orElseThrow(() -> new DataNotFoundException("Patient not found"));
        List<UserDiagnosticTestDto> testDtoList = new LinkedList<>();
        for (DiagnosticTestResultEntity testResult : testResults) {
            testDtoList.add(UserDiagnosticTestDto.builder()
                    .id(testResult.getId())
                    .date(testResult.getCreatedDate().toLocalDate())
                    .diagnosticTest(testResult.getTestName())
                    .hospitalName(testResult.getHospitalName())
                    .build());
        }
        return StandardResponse.<List<UserDiagnosticTestDto>>builder()
                .status(Status.SUCCESS)
                .message("Patient's diagnostic results")
                .data(testDtoList)
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
    public StandardResponse<DiagnosticTestResultEntity> getTestResult(UUID resultId){
        return StandardResponse.<DiagnosticTestResultEntity>builder()
                .status(Status.SUCCESS)
                .message("Diagnostic test result")
                .data(diagnosticTestResultRepository.findById(resultId).orElseThrow(() -> new DataNotFoundException("Test result not found")))
                .build();
    }
}
