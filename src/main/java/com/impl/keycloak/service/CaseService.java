package com.impl.keycloak.service;

import com.impl.keycloak.dto.CaseDto;
import com.impl.keycloak.mapper.CaseMapper;
import com.impl.keycloak.model.CaseEntity;
import com.impl.keycloak.repository.CaseRepository;
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
        return caseRepository.findById(id).map(CaseMapper::toDto);
    }

    public CaseDto create(CaseDto dto) {
        CaseEntity entity = CaseMapper.toEntity(dto);
        CaseEntity saved = caseRepository.save(entity);
        return CaseMapper.toDto(saved);
    }

    public Optional<CaseDto> update(Integer id, CaseDto dto) {
        return caseRepository.findById(id).map(existing -> {
            existing.setTitle(dto.getTitle());
            existing.setDescription(dto.getDescription());
            existing.setInvestigationStartDate(dto.getInvestigationStartDate());
            existing.setInvestigationStatus(dto.getInvestigationStatus());
            existing.setDetectiveName(dto.getDetectiveName());
            CaseEntity updated = caseRepository.save(existing);
            return CaseMapper.toDto(updated);
        });
    }

    public void delete(Integer id) {
        caseRepository.deleteById(id);
    }
}
