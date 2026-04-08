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
        uniqueConstraints = @UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"})
)
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @ManyToOne()
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
    private LocalDateTime criadoEm = LocalDateTime.now();

}
