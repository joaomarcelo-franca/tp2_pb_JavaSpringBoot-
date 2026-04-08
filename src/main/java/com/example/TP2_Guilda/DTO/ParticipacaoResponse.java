package com.example.TP2_Guilda.DTO;

import com.example.TP2_Guilda.Enum.FuncaoMissao;

public record ParticipacaoResponse(
        Long id,
        AventureiroResumoDTO aventureiro,
        FuncaoMissao funcaoMissao,
        Long recompensaOuro,
        Boolean mvp
) {
}
