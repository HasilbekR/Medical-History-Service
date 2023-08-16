package com.example.medicalhistoryservice.controller;

import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.service.DiagnosticTestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medical-history/api/v1/test-result")
@RequiredArgsConstructor
public class DiagnosticTestController {
    private final DiagnosticTestResultService diagnosticTestResultService;
    @PostMapping("/save")
    public DiagnosticTestResultEntity save(
            @RequestBody DiagnosticTestResultEntity diagnosticTestResultEntity
    ){
        return diagnosticTestResultService.save(diagnosticTestResultEntity);
    }
    @GetMapping("/get")
    public List<DiagnosticTestResultEntity> get(
            @RequestParam UUID patientId
            ){
        return diagnosticTestResultService.getPatientTestResults(patientId);
    }
    @PutMapping("/update")
    public DiagnosticTestResultEntity update(
            @RequestBody DiagnosticTestResultEntity diagnosticTestResultEntity,
            @RequestParam UUID resultId
    ){
        return diagnosticTestResultService.update(resultId,diagnosticTestResultEntity);
    }
}
