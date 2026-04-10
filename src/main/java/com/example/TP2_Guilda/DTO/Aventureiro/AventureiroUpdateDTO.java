package com.example.TP2_Guilda.DTO.Aventureiro;

import com.example.TP2_Guilda.Enum.Classe;

public record AventureiroUpdateDTO(
        String nome,
        Integer nivel,
        Classe classe
) {
}
