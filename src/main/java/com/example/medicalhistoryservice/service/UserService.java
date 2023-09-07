package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.response.ExchangeDataDto;
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
    @Value("${services.get-user-id}")
    private String getUserId;
    @Value("${services.get-doctor}")
    private String getUserEntity;
    private final JwtService jwtService;

    public String findUserEmailById(UUID userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create(getUserEmail),
                HttpMethod.POST,
                makeHttpEntity(userId.toString()),
                String.class);
        return Objects.requireNonNull(response.getBody());
    }
    public UUID findUserIdByEmail(String email) {
        ResponseEntity<UUID> response = restTemplate.exchange(
                URI.create(getUserId),
                HttpMethod.POST,
                makeHttpEntity(email),
                UUID.class);
        return Objects.requireNonNull(response.getBody());
    }
    public DoctorDetailsForBooking findDoctorNameById(UUID userId) {
        ResponseEntity<DoctorDetailsForBooking> response = restTemplate.exchange(
                URI.create(getUserEntity),
                HttpMethod.POST,
                makeHttpEntity(userId.toString()),
                DoctorDetailsForBooking.class);
        return Objects.requireNonNull(response.getBody());
    }
    public HttpEntity<ExchangeDataDto> makeHttpEntity(String source){
        ExchangeDataDto exchangeDataDto = new ExchangeDataDto(source);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + jwtService.generateAccessTokenForService("USER_SERVICE"));
        return new HttpEntity<>(exchangeDataDto, httpHeaders);
    }
}

