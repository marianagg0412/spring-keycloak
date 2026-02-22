package com.impl.keycloak.victims.service;

import com.impl.keycloak.victims.dto.VictimDto;
import com.impl.keycloak.victims.mapper.VictimMapper;
import com.impl.keycloak.cases.model.CaseEntity;
import com.impl.keycloak.victims.model.Victim;
import com.impl.keycloak.cases.repository.CaseRepository;
import com.impl.keycloak.victims.repository.VictimRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VictimService {

    private final VictimRepository victimRepository;
    private final CaseRepository caseRepository;

    public VictimService(VictimRepository victimRepository, CaseRepository caseRepository) {
        this.victimRepository = victimRepository;
        this.caseRepository = caseRepository;
    }

    public List<VictimDto> findAll() {
        return victimRepository.findAll().stream().map(VictimMapper::toDto).collect(Collectors.toList());
    }

    public Optional<VictimDto> findById(Integer id) {
        return victimRepository.findById(id).map(VictimMapper::toDto);
    }

    public VictimDto create(VictimDto dto) {
        Victim entity = VictimMapper.toEntity(dto);
        if (dto.getCaseId() != null) {
            caseRepository.findById(dto.getCaseId()).ifPresent(entity::setCaseEntity);
        }
        Victim saved = victimRepository.save(entity);
        return VictimMapper.toDto(saved);
    }

    public Optional<VictimDto> update(Integer id, VictimDto dto) {
        return victimRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setSurname(dto.getSurname());
            existing.setDateOfDeath(dto.getDateOfDeath());
            existing.setCauseOfDeath(dto.getCauseOfDeath());
            existing.setSocialStatus(dto.getSocialStatus());
            if (dto.getCaseId() != null) {
                caseRepository.findById(dto.getCaseId()).ifPresent(existing::setCaseEntity);
            } else {
                existing.setCaseEntity(null);
            }
            Victim updated = victimRepository.save(existing);
            return VictimMapper.toDto(updated);
        });
    }

    public void delete(Integer id) {
        victimRepository.deleteById(id);
    }
}
