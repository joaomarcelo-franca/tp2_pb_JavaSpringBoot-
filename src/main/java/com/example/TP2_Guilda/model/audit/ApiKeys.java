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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apiKeys_Sequence")
    @SequenceGenerator(name = "apiKeys_Sequence", sequenceName = "api_keys_id_seq",  allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @OneToMany(mappedBy = "apiKey")
    private List<AuditEntry> auditEntries;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "key_hash", nullable = false)
    private String hashKey;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "last_used_at")
    private LocalDateTime ultimaVezUsadoEm;



}
