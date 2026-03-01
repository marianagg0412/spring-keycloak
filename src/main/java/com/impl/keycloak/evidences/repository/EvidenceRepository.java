package com.impl.keycloak.evidences.repository;

import com.impl.keycloak.evidences.model.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenceRepository extends JpaRepository<Evidence,Integer> {
}
