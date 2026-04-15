package com.example.TP2_Guilda.dto.participacao;

import com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.dto.missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.Enum.FuncaoMissao;

import java.time.LocalDateTime;

public record ParticipacaoResponseComMissaoDTO(
        Long id,
        AventureiroResumoDTO aventureiroResumoDTO,
        MissaoResponseResumoDTO missaoResumoDto,
        Long recompensaOuro,
        FuncaoMissao funcaoMissao,
        Boolean mvp,
        LocalDateTime criadoEm
) {

}
