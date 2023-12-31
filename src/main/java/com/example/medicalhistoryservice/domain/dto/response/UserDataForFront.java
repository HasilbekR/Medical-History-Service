package com.example.medicalhistoryservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataForFront {
    private List<UserMedHistoryDto> histories;
    private List<UserDiagnosticTestDto> testResults;
    private Integer pageCount;
}
