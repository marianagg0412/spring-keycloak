package com.impl.keycloak.cases.controller;

import com.impl.keycloak.cases.dto.CaseDto;
import com.impl.keycloak.cases.service.CaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE', 'VIEWER')")
    public List<CaseDto> list() {
        return caseService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE', 'VIEWER')")
    public ResponseEntity<CaseDto> get(@PathVariable Integer id) {
        return caseService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE')")
    public ResponseEntity<CaseDto> create(@RequestBody CaseDto dto) {
        CaseDto created = caseService.create(dto);
        return ResponseEntity.created(URI.create("/api/cases/" + created.getCaseId())).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DETECTIVE')")
    public ResponseEntity<CaseDto> update(@PathVariable Integer id, @RequestBody CaseDto dto) {
        return caseService.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        caseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
