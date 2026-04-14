package com.example.TP2_Guilda.DTO.missao;

import com.example.TP2_Guilda.DTO.aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.DTO.participacao.ParticipacaoResponse;
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
