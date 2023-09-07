package com.example.medicalhistoryservice.controller;

import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.dto.response.UserDiagnosticTestDto;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.service.DiagnosticTestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medical-history/test-result")
@RequiredArgsConstructor
public class DiagnosticTestController {
    private final DiagnosticTestResultService diagnosticTestResultService;
    @PostMapping("/save")
    @PreAuthorize("hasRole('LAB')")
    public StandardResponse<DiagnosticTestResultEntity> save(
            @RequestBody DiagnosticTestResultEntity diagnosticTestResultEntity,
            Principal principal
    ){
        return diagnosticTestResultService.save(diagnosticTestResultEntity, principal);
    }
    @GetMapping("/get-test-results")
    public StandardResponse<List<UserDiagnosticTestDto>> get(
            @RequestParam UUID patientId
            ){
        return diagnosticTestResultService.getPatientTestResults(patientId);
    }
    @PutMapping("/update")
    public StandardResponse<DiagnosticTestResultEntity> update(
            @RequestBody DiagnosticTestResultEntity diagnosticTestResultEntity,
            @RequestParam UUID resultId
    ){
        return diagnosticTestResultService.update(resultId,diagnosticTestResultEntity);
    }
    @GetMapping("/get-test")
    public StandardResponse<DiagnosticTestResultEntity> getTestResult(
            @RequestParam UUID resultId
    ){
        return diagnosticTestResultService.getTestResult(resultId);
    }
}
