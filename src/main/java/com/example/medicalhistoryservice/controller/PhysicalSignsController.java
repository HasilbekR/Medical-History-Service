package com.example.medicalhistoryservice.controller;

import com.example.medicalhistoryservice.domain.dto.request.PhysicalSignsDto;
import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.entity.PhysicalSignsEntity;
import com.example.medicalhistoryservice.exception.RequestValidationException;
import com.example.medicalhistoryservice.service.PhysicalSignsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/medical-history/physical-signs")
@RequiredArgsConstructor
public class PhysicalSignsController {
    private final PhysicalSignsService physicalSignsService;

    @PostMapping("/save")
    public StandardResponse<PhysicalSignsEntity> save(
            @RequestBody PhysicalSignsDto entity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return physicalSignsService.save(entity);
    }

    @PutMapping("/update")
    public StandardResponse<PhysicalSignsEntity> update(
            @RequestBody PhysicalSignsDto entity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return physicalSignsService.update(entity);
    }

    @GetMapping("/get")
    public StandardResponse<PhysicalSignsEntity> get(
            @RequestParam UUID patientId
    ) {
        return physicalSignsService.get(patientId);
    }
}
