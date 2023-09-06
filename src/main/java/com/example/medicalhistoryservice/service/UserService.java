package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.ExchangeDataDto;
import com.example.medicalhistoryservice.domain.dto.request.DoctorDetailsForBooking;
import com.example.medicalhistoryservice.service.authentication.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;
    @Value("${services.get-user-email}")
    private String getUserEmail;
    @Value("${services.get-doctor}")
    private String getUserEntity;
    private final JwtService jwtService;

    public String findUserEmailById(UUID userId) {
        ExchangeDataDto userBookingRequestDto = new ExchangeDataDto(String.valueOf(userId));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + jwtService.generateAccessTokenForService("USER_SERVICE"));
        HttpEntity<ExchangeDataDto> entity = new HttpEntity<>(userBookingRequestDto, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create(getUserEmail),
                HttpMethod.POST,
                entity,
                String.class);
        return Objects.requireNonNull(response.getBody());
    }
    public DoctorDetailsForBooking findDoctorNameById(UUID userId) {
        ExchangeDataDto exchangeDataDto = new ExchangeDataDto(userId.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + jwtService.generateAccessTokenForService("USER_SERVICE"));
        HttpEntity<ExchangeDataDto> entity = new HttpEntity<>(exchangeDataDto, httpHeaders);
        ResponseEntity<DoctorDetailsForBooking> response = restTemplate.exchange(
                URI.create(getUserEntity),
                HttpMethod.POST,
                entity,
                DoctorDetailsForBooking.class);
        return Objects.requireNonNull(response.getBody());
    }
}

