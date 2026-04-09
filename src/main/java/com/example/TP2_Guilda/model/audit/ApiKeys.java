package com.example.TP2_Guilda.model.audit;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "api_keys",
        schema = "audit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id", "nome"}))
@Getter @Setter@AllArgsConstructor@NoArgsConstructor
public class ApiKeys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @OneToMany(mappedBy = "apiKey")
    private List<AuditEntry> auditEntries;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "key_hash", nullable = false, length = 255)
    private String hashKey;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "last_used_at", nullable = false)
    private LocalDateTime ultimaVezUsadoEm;



}
