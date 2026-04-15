package com.example.TP2_Guilda.dto.companheiro;

import com.example.TP2_Guilda.Enum.Especie;

public record CompanheiroResponseDTO(
        Long id,
        String nome,
        Especie especie,
        Integer lealdade
) {
}
