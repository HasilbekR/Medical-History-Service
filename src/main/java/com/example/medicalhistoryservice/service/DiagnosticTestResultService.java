package com.example.medicalhistoryservice.service;

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
    public DiagnosticTestResultEntity save(DiagnosticTestResultEntity diagnosticTestResultEntity){
        return diagnosticTestResultRepository.save(diagnosticTestResultEntity);
    }
    public List<DiagnosticTestResultEntity> getPatientTestResults(UUID patientId){
        return diagnosticTestResultRepository.findDiagnosticTestResultEntitiesByPatientId(patientId).orElseThrow(()-> new DataNotFoundException("Patient not found"));
    }
    public DiagnosticTestResultEntity update(UUID resultId, DiagnosticTestResultEntity diagnosticTestResultEntity){
        DiagnosticTestResultEntity result = diagnosticTestResultRepository.findById(resultId).orElseThrow(() -> new DataNotFoundException("Test result not found"));
        modelMapper.map(diagnosticTestResultEntity,result);
        return diagnosticTestResultRepository.save(result);
    }
}
