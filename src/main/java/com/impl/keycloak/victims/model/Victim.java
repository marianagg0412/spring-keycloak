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

    private String name;

    private String surname;

    private LocalDateTime dateOfDeath;

    private String causeOfDeath;

    private String socialStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private com.impl.keycloak.cases.model.CaseEntity caseEntity;

}
