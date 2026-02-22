package com.impl.keycloak.controller;

import com.impl.keycloak.dto.VictimDto;
import com.impl.keycloak.service.VictimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/victims")
public class VictimController {

    private final VictimService victimService;

    public VictimController(VictimService victimService) {
        this.victimService = victimService;
    }

    @GetMapping
    public List<VictimDto> list() {
        return victimService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VictimDto> get(@PathVariable Integer id) {
        return victimService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VictimDto> create(@RequestBody VictimDto dto) {
        VictimDto created = victimService.create(dto);
        return ResponseEntity.created(URI.create("/api/victims/" + created.getVictimId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VictimDto> update(@PathVariable Integer id, @RequestBody VictimDto dto) {
        return victimService.update(id, dto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        victimService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
