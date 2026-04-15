package com.example.TP2_Guilda.mappers;

import com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.dto.missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoResponseComMissaoDTO;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Missao;
import com.example.TP2_Guilda.model.aventura.Participacao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParticipacaoMapper {

    public static ParticipacaoResponseComMissaoDTO participacaoResponseComMissaoDTO(Participacao participacao, AventureiroResumoDTO aventureiroResumoDTO, MissaoResponseResumoDTO missaoResumoDTO){
        return new ParticipacaoResponseComMissaoDTO(
                participacao.getId(),
                aventureiroResumoDTO,
                missaoResumoDTO,
                participacao.getRecompensaOuro(),
                participacao.getFuncaoMissao(),
                participacao.isMvp(),
                participacao.getCriadoEm()
        );
    }

    public static ParticipacaoResponseComMissaoDTO participacaoResponseComMissaoDTO2(Participacao participacao, Aventureiro aventureiro, Missao missao){
        return new ParticipacaoResponseComMissaoDTO(
                participacao.getId(),
                AventureiroMapper.toAventureiroResumoDTO(aventureiro),
                MissaoMapper.toMissaoResponseResumoDTO(missao),
                participacao.getRecompensaOuro(),
                participacao.getFuncaoMissao(),
                participacao.isMvp(),
                participacao.getCriadoEm()
        );
    }
}
