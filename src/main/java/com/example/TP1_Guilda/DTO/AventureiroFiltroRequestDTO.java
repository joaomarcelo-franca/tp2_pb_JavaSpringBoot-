package com.example.TP1_Guilda.DTO;

import com.example.TP1_Guilda.Enum.Classe;

public record AventureiroFiltroRequestDTO(
        Classe classe,
        Boolean ativo,
        Integer nivel
) {
}
