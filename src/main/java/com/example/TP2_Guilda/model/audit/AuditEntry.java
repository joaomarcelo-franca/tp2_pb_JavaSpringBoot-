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
    private LocalDateTime occurredAt = LocalDateTime.now();

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "diff")
    private String diff;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "sucess")
    private String sucess;

    @ManyToOne(optional = false)
    @JoinColumn(name = "actor_api_key_id", nullable = false)
    private ApiKeys apiKey;

    @ManyToOne(optional = false)
    @JoinColumn(name = "actor_user_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "audit_id", nullable = false)
    private Organizacao organizacao;
}
