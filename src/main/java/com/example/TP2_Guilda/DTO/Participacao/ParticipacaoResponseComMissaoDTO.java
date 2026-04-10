package com.example.TP2_Guilda.DTO.Participacao;

import com.example.TP2_Guilda.DTO.Aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
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
