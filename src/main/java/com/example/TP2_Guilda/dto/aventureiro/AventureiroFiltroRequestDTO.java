package com.example.TP2_Guilda.dto.aventureiro;

import com.example.TP2_Guilda.Enum.Classe;

public record AventureiroFiltroRequestDTO(
        Classe classe,
        Boolean ativo,
        Integer nivel
) {
}
