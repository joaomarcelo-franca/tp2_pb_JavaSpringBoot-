package com.example.TP2_Guilda.mappers;

import com.example.TP2_Guilda.dto.aventureiro.AventureiroResponseDTO;
import com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.dto.aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.dto.companheiro.CompanheiroResponseDTO;
import com.example.TP2_Guilda.dto.missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class  AventureiroMapper {

    public static AventureiroResumoDTO toAventureiroResumoDTO(Aventureiro aventureiro) {
        return new AventureiroResumoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }

    public static AventureiroResponseDTO toAventureiroResponseDTO(Aventureiro aventureiro, OrganizacaoResumoDTO organizacaoResumoDTO, CompanheiroResponseDTO companheiroResponseDTO, Integer participacoes, MissaoResponseResumoDTO ultimaMissao) {
        return new AventureiroResponseDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo(),
                organizacaoResumoDTO,
                companheiroResponseDTO,
                aventureiro.getCriadoEm(),
                aventureiro.getAtualizadoEm(),
                participacoes,
                ultimaMissao
        );
    }
}
