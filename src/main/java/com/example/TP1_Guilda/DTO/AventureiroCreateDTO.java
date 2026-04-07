package com.example.TP1_Guilda.DTO;

import com.example.TP1_Guilda.Enum.Classe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AventureiroCreateDTO(
        @NotBlank(message = "nome é obrigatório")@NotNull(message = "nome é obrigatório")
        String nome,
        @NotNull(message = "classe é obrigatória")
        Classe classe,
        @Min(value = 1, message = "nivel deve ser maior ou igual a 1")
        Integer nivel

) {
}
