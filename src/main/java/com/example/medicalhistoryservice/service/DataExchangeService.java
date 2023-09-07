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
public class DataExchangeService {
    private final RestTemplate restTemplate;
    @Value("${services.get-user-email}")
    private String getUserEmail;
    @Value("${services.get-user-id}")
    private String getUserId;
    @Value("${services.get-hospital-id}")
    private String getHospitalId;
    @Value("${services.get-doctor}")
    private String getUserEntity;
    @Value("${services.get-hospital-name}")
    private String getHospitalName;

    private final JwtService jwtService;

    public String findUserEmailById(UUID userId) {
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create(getUserEmail),
                HttpMethod.POST,
                makeHttpEntity(userId.toString(), "USER-SERVICE"),
                String.class);
        return Objects.requireNonNull(response.getBody());
    }
    public UUID findUserIdByEmail(String email) {
        ResponseEntity<UUID> response = restTemplate.exchange(
                URI.create(getUserId),
                HttpMethod.POST,
                makeHttpEntity(email, "USER-SERVICE"),
                UUID.class);
        return Objects.requireNonNull(response.getBody());
    }
    public UUID findHospitalId(String employeeEmail) {
        ResponseEntity<UUID> response = restTemplate.exchange(
                URI.create(getHospitalId),
                HttpMethod.POST,
                makeHttpEntity(employeeEmail, "USER-SERVICE"),
                UUID.class);
        return Objects.requireNonNull(response.getBody());
    }

    public DoctorDetailsForBooking findDoctorNameById(UUID userId) {
        ResponseEntity<DoctorDetailsForBooking> response = restTemplate.exchange(
                URI.create(getUserEntity),
                HttpMethod.POST,
                makeHttpEntity(userId.toString(), "USER-SERVICE"),
                DoctorDetailsForBooking.class);
        return Objects.requireNonNull(response.getBody());
    }
    public String findHospitalName(UUID hospitalId) {
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create(getHospitalName),
                HttpMethod.POST,
                makeHttpEntity(hospitalId.toString(), "HOSPITAL-SERVICE"),
                String.class);
        return Objects.requireNonNull(response.getBody());
    }
    public HttpEntity<ExchangeDataDto> makeHttpEntity(String source, String service){
        ExchangeDataDto exchangeDataDto = new ExchangeDataDto(source);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + jwtService.generateAccessTokenForService(service));
        return new HttpEntity<>(exchangeDataDto, httpHeaders);
    }
}

