package com.example.TP2_Guilda.DTO.Aventureiro;

import com.example.TP2_Guilda.DTO.CompanheiroResponseDTO;
import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.Enum.Classe;
import com.example.TP2_Guilda.model.audit.Organizacao;
import com.example.TP2_Guilda.model.audit.Usuario;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import com.example.TP2_Guilda.model.aventura.Participacao;

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
