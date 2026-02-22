package com.impl.keycloak.cases.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseDto {
    private Integer caseId;
    private String title;
    private String description;
    private LocalDateTime investigationStartDate;
    private String investigationStatus;
    private String detectiveName;
    private List<com.impl.keycloak.victims.dto.VictimDto> victims;
}
