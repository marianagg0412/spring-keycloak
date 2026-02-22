package com.impl.keycloak.repository;

import com.impl.keycloak.model.Victim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VictimRepository extends JpaRepository<Victim, Integer> {
}
