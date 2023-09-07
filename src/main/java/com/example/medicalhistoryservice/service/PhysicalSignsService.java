package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.PhysicalSignsDto;
import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.dto.response.Status;
import com.example.medicalhistoryservice.domain.entity.PhysicalSignsEntity;
import com.example.medicalhistoryservice.exception.DataNotFoundException;
import com.example.medicalhistoryservice.repository.PhysicalSignsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhysicalSignsService {
    private final PhysicalSignsRepository physicalSignsRepository;
    private final ModelMapper modelMapper;
    public StandardResponse<PhysicalSignsEntity> save(PhysicalSignsDto physicalSignsDto){
        PhysicalSignsEntity physicalSigns = modelMapper.map(physicalSignsDto, PhysicalSignsEntity.class);
        return StandardResponse.<PhysicalSignsEntity>builder()
                .status(Status.SUCCESS)
                .message("Patient's physical signs successfully saved")
                .data(physicalSignsRepository.save(physicalSigns))
                .build();
    }
    public StandardResponse<PhysicalSignsEntity> update(PhysicalSignsDto physicalSignsDto){
        PhysicalSignsEntity physicalSigns = physicalSignsRepository.findById(physicalSignsDto.getPatientId()).orElseThrow(() -> new DataNotFoundException("Patient not found"));
        modelMapper.map(physicalSignsDto, physicalSigns);
        return StandardResponse.<PhysicalSignsEntity>builder()
                .status(Status.SUCCESS)
                .message("Patient's physical signs successfully updated")
                .data(physicalSignsRepository.save(physicalSigns))
                .build();
    }
    public StandardResponse<PhysicalSignsEntity> get(UUID patientId){
        return StandardResponse.<PhysicalSignsEntity>builder()
                .status(Status.SUCCESS)
                .message("Patient's physical signs")
                .data(physicalSignsRepository.findPhysicalSignsEntityByPatientId(patientId).orElseThrow(()-> new DataNotFoundException("Patient not found")))
                .build();
    }
}
