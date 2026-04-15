package com.example.TP2_Guilda.mappers;

import com.example.TP2_Guilda.dto.aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.model.audit.Organizacao;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class OrganizationMapper {
    public static OrganizacaoResumoDTO toOrganizacaoResumoDTO(Organizacao organizacao, LocalDateTime criadoEm) {
        return new OrganizacaoResumoDTO(
                organizacao.getId(),
                organizacao.getNome(),
                organizacao.isAtivo(),
                criadoEm
        );
    }
}
