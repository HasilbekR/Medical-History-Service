package com.example.medicalhistoryservice.service;

import com.example.medicalhistoryservice.domain.dto.UserRequestDto;
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
    @Value("${services.get-by-user-id}")
    private String getUserById;

    public String  findUserEmailById(UUID userId) {
        UserRequestDto userBookingRequestDto = new UserRequestDto(String.valueOf(userId));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRequestDto> entity = new HttpEntity<>(userBookingRequestDto, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create(getUserById),
                HttpMethod.POST,
                entity,
                String.class);
        System.out.println(Objects.requireNonNull(response.getBody()));
        return Objects.requireNonNull(response.getBody());
    }
}
