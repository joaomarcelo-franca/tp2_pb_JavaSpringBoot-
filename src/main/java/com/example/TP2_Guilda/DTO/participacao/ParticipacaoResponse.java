package com.example.TP2_Guilda.DTO.participacao;

import com.example.TP2_Guilda.DTO.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.Enum.FuncaoMissao;

public record ParticipacaoResponse(
        Long id,
        AventureiroResumoDTO aventureiro,
        FuncaoMissao funcaoMissao,
        Long recompensaOuro,
        boolean mvp
) {
}
