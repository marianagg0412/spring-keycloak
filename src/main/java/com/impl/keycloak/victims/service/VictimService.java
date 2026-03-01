package com.impl.keycloak.victims.service;

import com.impl.keycloak.common.exception.ResourceNotFoundException;
import com.impl.keycloak.victims.dto.VictimDto;
import com.impl.keycloak.victims.mapper.VictimMapper;
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
        return Optional.of(victimRepository.findById(id)
                .map(VictimMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Victim not found with id: " + id)));
    }

    public VictimDto create(VictimDto dto) {
        Victim entity = VictimMapper.toEntity(dto);
        if (dto.getCaseId() != null) {
            entity.setCaseEntity(
                    caseRepository.findById(dto.getCaseId())
                            .orElseThrow(() -> new ResourceNotFoundException("Case not found with id: " + dto.getCaseId()))
            );
        }
        return VictimMapper.toDto(victimRepository.save(entity));
    }

    public Optional<VictimDto> update(Integer id, VictimDto dto) {
        Victim existing = victimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Victim not found with id: " + id));

        existing.setName(dto.getName());
        existing.setSurname(dto.getSurname());
        existing.setDateOfDeath(dto.getDateOfDeath());
        existing.setCauseOfDeath(dto.getCauseOfDeath());
        existing.setSocialStatus(dto.getSocialStatus());

        if (dto.getCaseId() != null) {
            existing.setCaseEntity(
                    caseRepository.findById(dto.getCaseId())
                            .orElseThrow(() -> new ResourceNotFoundException("Case not found with id: " + dto.getCaseId()))
            );
        } else {
            existing.setCaseEntity(null);
        }

        return Optional.of(VictimMapper.toDto(victimRepository.save(existing)));
    }

    public void delete(Integer id) {
        if (!victimRepository.existsById(id)) {
            throw new ResourceNotFoundException("Victim not found with id: " + id);
        }
        victimRepository.deleteById(id);
    }
}
