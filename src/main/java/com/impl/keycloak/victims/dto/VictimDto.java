package com.impl.keycloak.victims.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VictimDto {
    private Integer victimId;
    private String name;
    private String surname;
    private LocalDateTime dateOfDeath;
    private String causeOfDeath;
    private String socialStatus;
    private Integer caseId;
}
