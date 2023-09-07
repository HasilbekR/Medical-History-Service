package com.example.medicalhistoryservice.repository;

import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface DiagnosticTestResultRepository extends JpaRepository<DiagnosticTestResultEntity,UUID> {
    Page<DiagnosticTestResultEntity> findDiagnosticTestResultEntitiesByPatientIdOrderByCreatedDateDesc(UUID patientId, Pageable pageable);
    Long countByPatientId(UUID patientId);
}
