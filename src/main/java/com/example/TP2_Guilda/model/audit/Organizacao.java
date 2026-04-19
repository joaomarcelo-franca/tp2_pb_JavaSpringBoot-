package com.example.TP2_Guilda.model.audit;

import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Missao;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizacaoSequence")
    @SequenceGenerator(name = "organizacaoSequence", sequenceName = "organizacoes_id_seq",  allocationSize = 1)
    private Long id;

    @Column(name = "nome", unique = true, nullable = false, length = 120)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "organizacao")
    private List<ApiKeys> apiKeys;

    @OneToMany(mappedBy = "organizacao")
    private List<AuditEntry> auditEntries;

    @OneToMany(mappedBy = "organizacao")
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "organizacao")
    private List<Role> roles;

    @OneToMany(mappedBy = "organizacao")
    private List<Aventureiro> aventureiros;

    @OneToMany(mappedBy = "organizacao")
    private List<Missao> missoes;


//    HELPERS

    public void removerMissao(Missao missao) {
        this.missoes.remove(missao);
        missao.setOrganizacao(null);
    };

    public void removerAventureiro(Aventureiro aventureiro) {
        this.aventureiros.remove(aventureiro);
        aventureiro.setOrganizacao(null);
    }
}
