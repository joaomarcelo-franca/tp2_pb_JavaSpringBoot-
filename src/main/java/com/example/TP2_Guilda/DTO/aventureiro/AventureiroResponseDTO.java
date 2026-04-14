package com.example.TP2_Guilda.DTO.aventureiro;

import com.example.TP2_Guilda.DTO.companheiro.CompanheiroResponseDTO;
import com.example.TP2_Guilda.DTO.missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.Enum.Classe;

import java.time.LocalDateTime;

public record AventureiroResponseDTO(
         Long id,
         String nome,
         Classe classe,
         Integer nivel,
         Boolean ativo,
         OrganizacaoResumoDTO organizacao,
         CompanheiroResponseDTO companheiro,
         LocalDateTime criadoEm,
         LocalDateTime atualizadoEm,
         Integer totalMissoes,
         MissaoResponseResumoDTO ultimaMissao
) {
}
