package com.example.TP2_Guilda.DTO;

import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.model.audit.Organizacao;

import java.time.LocalDateTime;
import java.util.List;

public record MissaoResponseDetalharDTO(
        Long id,
        Organizacao organizacao,
        String titulo,
        Status status,
        LocalDateTime criadoEm,
        List<ParticipacaoResponse> participacoes
) {
}
