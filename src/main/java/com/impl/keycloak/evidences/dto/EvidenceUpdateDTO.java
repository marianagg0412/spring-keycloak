package com.impl.keycloak.evidences.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EvidenceUpdateDTO(
        @NotBlank(message = "Type is required")
        String type,

        String description,

        @NotBlank(message = "Location found is required")
        String locationFound,

        LocalDateTime dateCollected,

        @NotNull(message = "You must put a case id")
        @Positive(message = "Case id must be positive")
        Integer caseId
) {
}
