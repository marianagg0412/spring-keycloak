package com.impl.keycloak.evidences.controller;

import com.impl.keycloak.evidences.dto.EvidenceCreateDTO;
import com.impl.keycloak.evidences.dto.EvidenceResponseDTO;
import com.impl.keycloak.evidences.dto.EvidenceUpdateDTO;
import com.impl.keycloak.evidences.service.EvidenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evidences")
public class EvidenceController {

    private final EvidenceService evidenceService;
    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE', 'VIEWER')")
    public List<EvidenceResponseDTO> findAll() {
        return evidenceService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE', 'VIEWER')")
    public EvidenceResponseDTO findById(@PathVariable Integer id) {
        return evidenceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE')")
    public EvidenceResponseDTO create(@Valid @RequestBody EvidenceCreateDTO dto) {
        return evidenceService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE')")
    public EvidenceResponseDTO update(
            @PathVariable Integer id,
            @Valid @RequestBody EvidenceUpdateDTO dto
    ) {
        return evidenceService.updateById(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        evidenceService.deleteById(id);
    }
}
