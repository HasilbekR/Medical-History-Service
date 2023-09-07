package com.example.medicalhistoryservice.controller;

import com.example.medicalhistoryservice.domain.dto.request.MedicalHistoryDto;
import com.example.medicalhistoryservice.domain.dto.response.StandardResponse;
import com.example.medicalhistoryservice.domain.dto.response.UserDataForFront;
import com.example.medicalhistoryservice.domain.dto.response.UserMedHistoryDto;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.domain.entity.MedicalHistoryEntity;
import com.example.medicalhistoryservice.exception.AuthenticationFailedException;
import com.example.medicalhistoryservice.exception.RequestValidationException;
import com.example.medicalhistoryservice.service.MedicalHistoryService;
import com.example.medicalhistoryservice.service.DataExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical-history")
public class MedicalHistoryController
{
    private final MedicalHistoryService medicalHistoryService;
    private final DataExchangeService dataExchangeService;
    @PostMapping("/save")
    @PreAuthorize("hasRole('DOCTOR')")
    public StandardResponse<MedicalHistoryEntity> save(
            @RequestBody MedicalHistoryDto medicalHistoryDto,
            Principal principal,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return medicalHistoryService.save(medicalHistoryDto, principal);
    }

    @PostMapping("/set-test-result")
    @PreAuthorize("hasRole('LAB')")
    public StandardResponse<MedicalHistoryEntity> setResult(
            @RequestBody DiagnosticTestResultEntity result,
            @RequestParam UUID historyId,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return medicalHistoryService.setTestResult(historyId,result);
    }

    @GetMapping("/get-patient-all-history")
    public StandardResponse<UserDataForFront> getPatientHistory(
            @RequestParam UUID patientId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        String patientEmail = dataExchangeService.findUserEmailById(patientId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Get the user's email from the JWT token
        String userEmail = getEmailFromToken(authentication);

        if(isAdmin || Objects.equals(patientEmail, userEmail)){
            return medicalHistoryService.getPatientHistories(patientId,page, size);
        }else{
            throw new  AuthenticationFailedException("Forbidden user");
        }
    }
    @GetMapping("/get-med-history")
    public StandardResponse<MedicalHistoryEntity> getMedHistory(
            @RequestParam UUID historyId
    ){
        return medicalHistoryService.getMedHistory(historyId);
    }

    @GetMapping("/get-doctor-reports")
    public StandardResponse<List<MedicalHistoryEntity>> getDoctorReports(
            @RequestParam UUID doctorId
    ){
        String patientEmail = dataExchangeService.findUserEmailById(doctorId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Get the user's email from the JWT token
        String userEmail = getEmailFromToken(authentication);

        if(isAdmin || Objects.equals(patientEmail, userEmail)){
            return medicalHistoryService.getDoctorReports(doctorId);
        }else{
            throw new  AuthenticationFailedException("Forbidden user");
        }
    }
    public String getEmailFromToken(Authentication authentication) {
        return authentication.getPrincipal().toString();
    }

}
