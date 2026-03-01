package com.impl.keycloak.evidences.model;

import com.impl.keycloak.cases.model.CaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "evidences")
@Data
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evidence_id")
    private Integer evidenceId;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String locationFound;

    private LocalDateTime dateCollected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private CaseEntity caseEntity;

}
