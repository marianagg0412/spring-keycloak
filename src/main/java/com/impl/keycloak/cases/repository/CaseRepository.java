package com.impl.keycloak.cases.repository;

import com.impl.keycloak.cases.model.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<CaseEntity, Integer> {
}
