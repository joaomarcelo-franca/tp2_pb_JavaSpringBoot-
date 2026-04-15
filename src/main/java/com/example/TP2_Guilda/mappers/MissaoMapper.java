package com.example.TP2_Guilda.mappers;

import com.example.TP2_Guilda.dto.aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.dto.missao.MissaoResponseDetalharDTO;
import com.example.TP2_Guilda.dto.missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoResponse;
import com.example.TP2_Guilda.model.aventura.Missao;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MissaoMapper {

    public static MissaoResponseResumoDTO toMissaoResponseResumoDTO(Missao missao) {
        return new MissaoResponseResumoDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelDePerigo(),
                missao.getCriandoEm()
        );
    }

    public static MissaoResponseDetalharDTO toMissaoResponseDetalharDTO(Missao missao, OrganizacaoResumoDTO organizacaoResumoDTO, List<ParticipacaoResponse> participacoesDTO) {
        return new MissaoResponseDetalharDTO(
                missao.getId(),
                organizacaoResumoDTO,
                missao.getTitulo(),
                missao.getStatus(),
                missao.getCriandoEm(),
                participacoesDTO
        );
    }
}
