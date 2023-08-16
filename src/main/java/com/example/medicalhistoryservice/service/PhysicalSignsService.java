package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.PhysicalSignsDto;
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
    public PhysicalSignsEntity save(PhysicalSignsDto physicalSignsDto){
        PhysicalSignsEntity physicalSigns = modelMapper.map(physicalSignsDto, PhysicalSignsEntity.class);
        return physicalSignsRepository.save(physicalSigns);
    }
    public PhysicalSignsEntity update(PhysicalSignsDto physicalSignsDto){
        PhysicalSignsEntity physicalSigns = physicalSignsRepository.findById(physicalSignsDto.getPatientId()).orElseThrow(() -> new DataNotFoundException("Patient not found"));
        modelMapper.map(physicalSignsDto, physicalSigns);
        return physicalSignsRepository.save(physicalSigns);
    }
    public PhysicalSignsEntity get(UUID patientId){
        return physicalSignsRepository.findPhysicalSignsEntityByPatientId(patientId).orElseThrow(()-> new DataNotFoundException("Patient not found"));
    }
}
