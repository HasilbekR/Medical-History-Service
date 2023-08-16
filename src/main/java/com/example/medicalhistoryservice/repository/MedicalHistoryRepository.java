package com.example.medicalhistoryservice.repository;

import com.example.medicalhistoryservice.domain.entity.MedicalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, UUID> {
    Optional<List<MedicalHistoryEntity>> findMedicalHistoryEntitiesByPatientUuid(UUID patientId);
    Optional<List<MedicalHistoryEntity>> findMedicalHistoryEntitiesByDoctorUuid(UUID doctorId);


}
