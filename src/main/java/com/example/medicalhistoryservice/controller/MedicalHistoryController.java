package com.example.medicalhistoryservice.controller;

import com.example.medicalhistoryservice.domain.dto.MedicalHistoryDto;
import com.example.medicalhistoryservice.domain.entity.DiagnosticTestResultEntity;
import com.example.medicalhistoryservice.domain.entity.MedicalHistoryEntity;
import com.example.medicalhistoryservice.exception.AuthenticationFailedException;
import com.example.medicalhistoryservice.exception.RequestValidationException;
import com.example.medicalhistoryservice.service.MedicalHistoryService;
import com.example.medicalhistoryservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medical-history")
public class MedicalHistoryController
{
    private final MedicalHistoryService medicalHistoryService;
    private final UserService userService;
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public MedicalHistoryEntity save(
            @RequestBody MedicalHistoryDto medicalHistoryDto,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return medicalHistoryService.save(medicalHistoryDto);
    }

    @PostMapping("/set-test-result")
    @PreAuthorize("hasRole('LAB')")
    public MedicalHistoryEntity setResult(
            @RequestBody DiagnosticTestResultEntity result,
            @RequestParam UUID historyId,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors()){
            throw new RequestValidationException(bindingResult.getAllErrors());
        }
        return medicalHistoryService.setTestResult(historyId,result);
    }

    @GetMapping("/get-patient-history")
    public List<MedicalHistoryEntity> getPatientHistory(
            @RequestParam UUID patientId
    ){
        String patientEmail = userService.findUserEmailById(patientId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Get the user's email from the JWT token
        String userEmail = getEmailFromToken(authentication);

        if(isAdmin || Objects.equals(patientEmail, userEmail)){
            return medicalHistoryService.getPatientHistories(patientId);
        }else{
            throw new  AuthenticationFailedException("Forbidden user");
        }
    }
    public String getEmailFromToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
