package com.example.medicalhistoryservice.repository;

import com.example.medicalhistoryservice.domain.entity.MedicalHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntity, UUID> {
    Page<MedicalHistoryEntity> findMedicalHistoryEntitiesByPatientUuidOrderByCreatedDateDesc(UUID patientId, Pageable pageable);
    Optional<List<MedicalHistoryEntity>> findMedicalHistoryEntitiesByDoctorUuid(UUID doctorId);
    Long countByPatientUuid(UUID patientId);


}
