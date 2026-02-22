package com.impl.keycloak.cases.controller;

import com.impl.keycloak.cases.dto.CaseDto;
import com.impl.keycloak.cases.service.CaseService;
import org.springframework.http.ResponseEntity;
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
    public List<CaseDto> list() {
        return caseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseDto> get(@PathVariable Integer id) {
        return caseService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CaseDto> create(@RequestBody CaseDto dto) {
        CaseDto created = caseService.create(dto);
        return ResponseEntity.created(URI.create("/api/cases/" + created.getCaseId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaseDto> update(@PathVariable Integer id, @RequestBody CaseDto dto) {
        return caseService.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        caseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
