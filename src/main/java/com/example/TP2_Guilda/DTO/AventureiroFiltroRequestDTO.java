package com.example.TP2_Guilda.DTO;

import com.example.TP2_Guilda.Enum.Classe;

public record AventureiroFiltroRequestDTO(
        Classe classe,
        Boolean ativo,
        Integer nivel
) {
}
