package com.example.TP2_Guilda.DTO.Aventureiro;

import java.time.LocalDateTime;

public record OrganizacaoResumoDTO(
        Long id,
        String nome,
        boolean ativo,
        LocalDateTime criadoEm
) {
}
