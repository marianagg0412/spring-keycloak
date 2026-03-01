package com.impl.keycloak.evidences.mapper;

import com.impl.keycloak.cases.model.CaseEntity;
import com.impl.keycloak.evidences.dto.EvidenceCreateDTO;
import com.impl.keycloak.evidences.dto.EvidenceResponseDTO;
import com.impl.keycloak.evidences.dto.EvidenceUpdateDTO;
import com.impl.keycloak.evidences.model.Evidence;

public class EvidenceMapper {
    private  EvidenceMapper(){}

    public static EvidenceResponseDTO toEvidenceResponseDTO(Evidence evidence){
        Integer caseId= null;
        String caseTitle=null;
        if(evidence.getCaseEntity()!=null){
            caseId= evidence.getCaseEntity().getCaseId();
            caseTitle=evidence.getCaseEntity().getTitle();
        }
        return new EvidenceResponseDTO(
                evidence.getEvidenceId(),
                evidence.getType(),
                evidence.getDescription(),
                evidence.getLocationFound(),
                evidence.getDateCollected(),
                caseId,
                caseTitle
        );
    }

    public static Evidence toEntity(EvidenceCreateDTO dto, CaseEntity caseEntity){
        Evidence evidence = new Evidence();
        evidence.setType(dto.type());
        evidence.setDescription(dto.description());
        evidence.setLocationFound(dto.locationFound());
        evidence.setDateCollected(dto.dateCollected());
        evidence.setCaseEntity(caseEntity);
        return evidence;
    }

    public static void updateEntity(Evidence evidence, EvidenceUpdateDTO dto, CaseEntity caseEntity){
        evidence.setType(dto.type());
        evidence.setDescription(dto.description());
        evidence.setLocationFound(dto.locationFound());
        evidence.setDateCollected(dto.dateCollected());
        evidence.setCaseEntity(caseEntity);
    }
}
