package com.example.TP2_Guilda.model.aventura;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.model.audit.Organizacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.NamedAttributeNode;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedEntityGraph(
        name = "Missao.completo",
        attributeNodes = {
                @NamedAttributeNode("organizacao"),
                @NamedAttributeNode(value = "participacoes", subgraph = "participacoes-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "participacoes-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("aventureiro")
                        }
                )
        }
)
@Entity
@Table(
        name = "missoes"
)
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @OneToMany(mappedBy = "missao", orphanRemoval = true)
    List<Participacao> participacoes;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "Perigo")
    private NivelDePerigo nivelDePerigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime criandoEm;

    public Missao(String titulo, Organizacao organizacao, NivelDePerigo nivelDePerigo, Status status) {
        this.titulo = titulo;
        this.organizacao = organizacao;
        this.nivelDePerigo = nivelDePerigo;
        this.status = status;
        this.criandoEm = LocalDateTime.now();
    }

    protected Missao() {}
}
