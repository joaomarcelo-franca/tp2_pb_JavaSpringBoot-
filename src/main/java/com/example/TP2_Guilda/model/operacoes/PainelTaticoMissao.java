package com.example.TP2_Guilda.model.operacoes;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(
        schema = "operacoes",
        name = "vw_painel_tatico_missao"
)
@Getter
@Setter
@NoArgsConstructor
public class PainelTaticoMissao {
    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo", length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", length = 20)
    private NivelDePerigo nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Long totalParticipantes;

    @Column(name = "nivel_medio_equipe", precision = 10, scale = 2)
    private BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps")
    private Long totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Long participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private BigDecimal indiceProntidao;
}
