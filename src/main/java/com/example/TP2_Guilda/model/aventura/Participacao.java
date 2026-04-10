package com.example.TP2_Guilda.model.aventura;

import com.example.TP2_Guilda.Enum.FuncaoMissao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
       name = "participacoes_missao",
        schema = "aventura",
        uniqueConstraints = @UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"})
)
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @Column(name = "recompensa_ouro")
    @PositiveOrZero
    private Long recompensaOuro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuncaoMissao funcaoMissao;

    @Column(nullable = false)
    private boolean mvp;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    protected Participacao() {}

    public Participacao(Aventureiro aventureiro, Missao missao, Long recompensaOuro, FuncaoMissao funcaoMissao, boolean mvp) {
        this.aventureiro = aventureiro;
        this.missao = missao;
        this.recompensaOuro = recompensaOuro;
        this.funcaoMissao = funcaoMissao;
        this.mvp = mvp;
        this.criadoEm = LocalDateTime.now();
    }
}
