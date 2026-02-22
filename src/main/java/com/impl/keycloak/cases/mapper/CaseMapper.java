package com.impl.keycloak.cases.mapper;

import com.impl.keycloak.cases.dto.CaseDto;
import com.impl.keycloak.victims.dto.VictimDto;
import com.impl.keycloak.cases.model.CaseEntity;
import com.impl.keycloak.victims.model.Victim;

import java.util.List;
import java.util.stream.Collectors;

public class CaseMapper {

    public static CaseDto toDto(CaseEntity entity) {
        if (entity == null) return null;
        List<VictimDto> victims = null;
        if (entity.getVictims() != null) {
            victims = entity.getVictims().stream().map(com.impl.keycloak.victims.mapper.VictimMapper::toDto).collect(Collectors.toList());
        }
        return CaseDto.builder()
                .caseId(entity.getCaseId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .investigationStartDate(entity.getInvestigationStartDate())
                .investigationStatus(entity.getInvestigationStatus())
                .detectiveName(entity.getDetectiveName())
                .victims(victims)
                .build();
    }

    public static CaseEntity toEntity(CaseDto dto) {
        if (dto == null) return null;
        CaseEntity entity = CaseEntity.builder()
                .caseId(dto.getCaseId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .investigationStartDate(dto.getInvestigationStartDate())
                .investigationStatus(dto.getInvestigationStatus())
                .detectiveName(dto.getDetectiveName())
                .build();
        if (dto.getVictims() != null) {
            List<Victim> victims = dto.getVictims().stream().map(com.impl.keycloak.victims.mapper.VictimMapper::toEntity).collect(Collectors.toList());
            victims.forEach(v -> v.setCaseEntity(entity));
            entity.setVictims(victims);
        }
        return entity;
    }
}
