package com.impl.keycloak.victims.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "victims")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Victim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "victim_id")
    private Integer victimId;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String surname;

    private LocalDateTime dateOfDeath;

    private String causeOfDeath;

    private String socialStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private com.impl.keycloak.cases.model.CaseEntity caseEntity;

}
