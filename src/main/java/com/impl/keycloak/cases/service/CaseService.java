package com.impl.keycloak.cases.service;

import com.impl.keycloak.cases.dto.CaseDto;
import com.impl.keycloak.cases.mapper.CaseMapper;
import com.impl.keycloak.cases.model.CaseEntity;
import com.impl.keycloak.cases.repository.CaseRepository;
import com.impl.keycloak.common.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public List<CaseDto> findAll() {
        return caseRepository.findAll().stream().map(CaseMapper::toDto).collect(Collectors.toList());
    }

    public Optional<CaseDto> findById(Integer id) {
        return Optional.of(caseRepository.findById(id)
                .map(CaseMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with id: " + id)));
    }

    public CaseDto create(CaseDto dto) {
        CaseEntity entity = CaseMapper.toEntity(dto);
        CaseEntity saved = caseRepository.save(entity);
        return CaseMapper.toDto(saved);
    }

    public Optional<CaseDto> update(Integer id, CaseDto dto) {
        CaseEntity existing = caseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Case not found with id: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setInvestigationStartDate(dto.getInvestigationStartDate());
        existing.setInvestigationStatus(dto.getInvestigationStatus());
        existing.setDetectiveName(dto.getDetectiveName());

        return Optional.of(CaseMapper.toDto(caseRepository.save(existing)));
    }

    public void delete(Integer id) {
        if (!caseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Case not found with id: " + id);
        }
        caseRepository.deleteById(id);
    }
}
