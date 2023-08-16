package com.example.medicalhistoryservice.repository;

import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiagnosticTestResultRepository extends JpaRepository<DiagnosticTestResultEntity,UUID> {
    Optional<List<DiagnosticTestResultEntity>> findDiagnosticTestResultEntitiesByPatientId(UUID patientId);
}
