package com.impl.keycloak.evidences.dto;

import java.time.LocalDateTime;

public record EvidenceResponseDTO(
        Integer evidenceId,
        String type,
        String description,
        String locationFound,
        LocalDateTime dateCollected,
        Integer caseId,
        String caseTitle
) {
}
