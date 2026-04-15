package com.example.TP2_Guilda.dto.participacao;

import com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.Enum.FuncaoMissao;

public record ParticipacaoResponse(
        Long id,
        AventureiroResumoDTO aventureiro,
        FuncaoMissao funcaoMissao,
        Long recompensaOuro,
        boolean mvp
) {
}
