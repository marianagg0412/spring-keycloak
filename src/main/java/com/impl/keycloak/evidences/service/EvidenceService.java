package com.impl.keycloak.evidences.service;

import com.impl.keycloak.cases.model.CaseEntity;
import com.impl.keycloak.cases.repository.CaseRepository;
import com.impl.keycloak.common.exception.ResourceNotFoundException;
import com.impl.keycloak.evidences.dto.EvidenceCreateDTO;
import com.impl.keycloak.evidences.dto.EvidenceResponseDTO;
import com.impl.keycloak.evidences.dto.EvidenceUpdateDTO;
import com.impl.keycloak.evidences.mapper.EvidenceMapper;
import com.impl.keycloak.evidences.model.Evidence;
import com.impl.keycloak.evidences.repository.EvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final CaseRepository caseRepository;
    public EvidenceService(EvidenceRepository evidenceRepository, CaseRepository caseRepository) {
        this.evidenceRepository = evidenceRepository;
        this.caseRepository = caseRepository;
    }

    public List<EvidenceResponseDTO> findAll( ){
        return evidenceRepository.findAll().stream().map(EvidenceMapper::toEvidenceResponseDTO)
                .collect(Collectors.toList());
    }

    public EvidenceResponseDTO findById(Integer id){
        Evidence evidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evidence with id " + id + " not found"
                ));

        return EvidenceMapper.toEvidenceResponseDTO(evidence);
    }

    public EvidenceResponseDTO create (EvidenceCreateDTO dto){
        CaseEntity caseEntity = caseRepository.findById(dto.caseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Case with id " + dto.caseId() + " not found"
                ));
        Evidence evidence = EvidenceMapper.toEntity(dto, caseEntity);
        Evidence savedEvidence = evidenceRepository.save(evidence);
        return EvidenceMapper.toEvidenceResponseDTO(savedEvidence);
    }

    public EvidenceResponseDTO updateById(Integer id, EvidenceUpdateDTO dto){
        Evidence evidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evidence with id " + id + " not found"
                ));
        CaseEntity caseEntity = caseRepository.findById(dto.caseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Case with id " + dto.caseId() + " not found"
                ));
        EvidenceMapper.updateEntity(evidence, dto, caseEntity);
        Evidence updatedEvidence = evidenceRepository.save(evidence);
        return EvidenceMapper.toEvidenceResponseDTO(updatedEvidence);
    }

    public void deleteById(Integer id){
        Evidence evidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Evidence with id " + id + " not found"
                ));
        evidenceRepository.delete(evidence);
    }
}
