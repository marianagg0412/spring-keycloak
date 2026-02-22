package com.impl.keycloak.victims.mapper;

import com.impl.keycloak.victims.dto.VictimDto;
import com.impl.keycloak.cases.model.CaseEntity;
import com.impl.keycloak.victims.model.Victim;

public class VictimMapper {

    public static VictimDto toDto(Victim entity) {
        if (entity == null) return null;
        Integer caseId = null;
        if (entity.getCaseEntity() != null) caseId = entity.getCaseEntity().getCaseId();
        return VictimDto.builder()
                .victimId(entity.getVictimId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .dateOfDeath(entity.getDateOfDeath())
                .causeOfDeath(entity.getCauseOfDeath())
                .socialStatus(entity.getSocialStatus())
                .caseId(caseId)
                .build();
    }

    public static Victim toEntity(VictimDto dto) {
        if (dto == null) return null;
        Victim entity = Victim.builder()
                .victimId(dto.getVictimId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .dateOfDeath(dto.getDateOfDeath())
                .causeOfDeath(dto.getCauseOfDeath())
                .socialStatus(dto.getSocialStatus())
                .build();
        if (dto.getCaseId() != null) {
            CaseEntity c = new CaseEntity();
            c.setCaseId(dto.getCaseId());
            entity.setCaseEntity(c);
        }
        return entity;
    }
}
