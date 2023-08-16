package com.example.medicalhistoryservice.repository;

import com.example.medicalhistoryservice.domain.entity.PhysicalSignsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicalSignsRepository extends JpaRepository<PhysicalSignsEntity, UUID> {
    Optional<PhysicalSignsEntity> findPhysicalSignsEntityByPatientId(UUID patientId);
}
