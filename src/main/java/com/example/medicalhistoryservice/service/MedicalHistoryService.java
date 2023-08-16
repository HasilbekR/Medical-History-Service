package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.MedicalHistoryDto;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.domain.entity.MedicalHistoryEntity;
import com.example.medicalhistoryservice.exception.DataNotFoundException;
import com.example.medicalhistoryservice.repository.MedicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final ModelMapper modelMapper;
    public MedicalHistoryEntity save(MedicalHistoryDto medicalHistoryDto){
        MedicalHistoryEntity medicalHistoryEntity = modelMapper.map(medicalHistoryDto, MedicalHistoryEntity.class);
        return medicalHistoryRepository.save(medicalHistoryEntity);
    }

    public MedicalHistoryEntity setTestResult(UUID historyId, DiagnosticTestResultEntity result){
        MedicalHistoryEntity historyEntity = medicalHistoryRepository.findById(historyId).orElseThrow(() -> new DataNotFoundException("History not found"));
        List<DiagnosticTestResultEntity> diagnosticTestResultEntities = historyEntity.getDiagnosticTestResultEntities();
        diagnosticTestResultEntities.add(result);
        historyEntity.setDiagnosticTestResultEntities(diagnosticTestResultEntities);
        return medicalHistoryRepository.save(historyEntity);
    }

    public List<MedicalHistoryEntity> getPatientHistories(UUID patientId){
        return medicalHistoryRepository.findMedicalHistoryEntitiesByPatientId(patientId).orElseThrow(() -> new DataNotFoundException("Data not found"));
    }
    public List<MedicalHistoryEntity> getDoctorReports(UUID doctorId){
        return medicalHistoryRepository.findMedicalHistoryEntitiesByPatientId(doctorId).orElseThrow(() -> new DataNotFoundException("Data not found"));
    }

}
