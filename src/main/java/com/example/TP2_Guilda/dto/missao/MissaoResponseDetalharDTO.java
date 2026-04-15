package com.example.TP2_Guilda.dto.missao;

import com.example.TP2_Guilda.dto.aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoResponse;
import com.example.TP2_Guilda.Enum.Status;

import java.time.LocalDateTime;
import java.util.List;

public record MissaoResponseDetalharDTO(
        Long id,
        OrganizacaoResumoDTO organizacao,
        String titulo,
        Status status,
        LocalDateTime criadoEm,
        List<ParticipacaoResponse> participacoes
) {
}
