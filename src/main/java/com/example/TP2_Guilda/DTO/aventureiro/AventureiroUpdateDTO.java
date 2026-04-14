package com.example.TP2_Guilda.DTO.aventureiro;

import com.example.TP2_Guilda.Enum.Classe;
import jakarta.validation.constraints.Positive;

public record AventureiroUpdateDTO(
        String nome,

        @Positive(message = "nivel tem que ser maior que ZERO")
        Integer nivel,
        Classe classe
) {
}
