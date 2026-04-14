package com.example.TP2_Guilda.DTO.aventureiro;

import com.example.TP2_Guilda.Enum.Classe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AventureiroCreateDTO(

        @NotNull(message = "OrganizacaoID é obrigatório")
        Long organizacaoId,

        @NotNull(message = "UserID é obrigatório")
        Long userId,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Classe é obrigatória")
        Classe classe,

        @Positive(message = "nivel tem que ser maior que ZERO")
        Integer nivel

) {
}
