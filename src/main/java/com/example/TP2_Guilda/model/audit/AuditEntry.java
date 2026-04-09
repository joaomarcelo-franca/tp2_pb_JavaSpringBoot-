package com.example.TP2_Guilda.model.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "audit_entries",
        schema = "audit"
)
@Getter @Setter@AllArgsConstructor
@NoArgsConstructor
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action", length = 30, nullable = false)
    private String action;

    @Column(name = "entity_schema", length = 30, nullable = false)
    private String entitySchema;

    @Column(name = "entity_name", length = 80, nullable = false)
    private String entityName;

    @Column(name = "entity_id", length = 80)
    private String entityId;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_agent", columnDefinition = "inet", nullable = false)
    private String userAgent;

    @Column(name = "diff", columnDefinition = "jsonb")
    private String diff;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "sucess")
    private String sucess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id")
    private ApiKeys apiKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;
}
