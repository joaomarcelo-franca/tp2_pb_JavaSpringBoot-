package com.example.TP1_Guilda.DTO;

import com.example.TP1_Guilda.Enum.Classe;

public record AventureiroResumoDTO(
        Long id,
        String nome,
        Classe classe,
        Integer nivel,
        Boolean ativo
) {}
