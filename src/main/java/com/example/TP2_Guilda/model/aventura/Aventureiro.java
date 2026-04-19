package com.example.TP2_Guilda.model.aventura;

import com.example.TP2_Guilda.Enum.Classe;
import com.example.TP2_Guilda.model.audit.Organizacao;
import com.example.TP2_Guilda.model.audit.Usuario;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter@Setter
@Table(
        name = "aventureiros",
        schema = "aventura"
)
@NamedEntityGraph(
        name = "Aventureiro.completo",
        attributeNodes = {
                @NamedAttributeNode("organizacao"),
                @NamedAttributeNode("usuario"),
                @NamedAttributeNode("companheiro"),
                @NamedAttributeNode("participacoes")
        }
)
@AllArgsConstructor
public class Aventureiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "companheiro_id", unique = true)
    private Companheiro companheiro;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "aventureiro")
    private List<Participacao> participacoes;


    @Column(length = 120, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Classe classe;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column(nullable = false)
    private LocalDateTime atualizadoEm;

    protected  Aventureiro() {
    }

    public Aventureiro(Organizacao organizacao, Usuario usuario, String nome, Classe classe, Integer nivel) {
        this.organizacao = organizacao;
        this.usuario = usuario;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = true;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    public void removerParticipacao(Participacao byId) {
        this.participacoes.remove(byId);
        byId.setAventureiro(null);
    }
}


