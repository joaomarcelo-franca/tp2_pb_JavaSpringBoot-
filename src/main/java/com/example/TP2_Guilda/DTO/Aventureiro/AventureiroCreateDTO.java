package com.example.TP2_Guilda.DTO.Aventureiro;

import com.example.TP2_Guilda.Enum.Classe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AventureiroCreateDTO(

        @NotNull
        Long organizacaoId,

        @NotNull
        Long userId,

        @NotBlank
        String nome,

        @NotNull
        Classe classe,

        @Positive
        Integer nivel

) {
}
