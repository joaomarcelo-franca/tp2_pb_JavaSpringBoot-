package com.example.TP2_Guilda.DTO.aventureiro;

import com.example.TP2_Guilda.Enum.Classe;

public record AventureiroResumoDTO(
        Long id,
        String nome,
        Classe classe,
        Integer nivel,
        Boolean ativo
) {}
