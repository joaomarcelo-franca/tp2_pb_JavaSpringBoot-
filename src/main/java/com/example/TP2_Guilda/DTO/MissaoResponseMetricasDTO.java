package com.example.TP2_Guilda.DTO;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;

public record MissaoResponseMetricasDTO(
        String titulo,
        Status status,
        NivelDePerigo nivelDePerigo,
        Long quantidadeParticipantes,
        Long totalRecompensas
) {
}
