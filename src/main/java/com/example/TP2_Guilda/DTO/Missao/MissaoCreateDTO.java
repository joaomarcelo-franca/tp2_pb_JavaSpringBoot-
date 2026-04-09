package com.example.TP2_Guilda.DTO.Missao;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record MissaoCreateDTO(

        @NotBlank
        @Length(min = 1, max = 150)
        String titulo,

        @NotNull
        NivelDePerigo nivelDePerigo,

        @NotNull
        Status status


) {
}
