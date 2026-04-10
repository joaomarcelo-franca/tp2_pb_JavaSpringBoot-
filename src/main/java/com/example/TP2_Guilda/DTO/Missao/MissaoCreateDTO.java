package com.example.TP2_Guilda.DTO.Missao;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record MissaoCreateDTO(

        @NotBlank(message = "Titulo nao pode ser vazio")
        @Length(min = 1, max = 150, message = "Titulo pode ter no máximo 150 caracteres")
        String titulo,

        @NotNull(message = "nivelDePerigo nao pode ser vazio")
        NivelDePerigo nivelDePerigo,

        @NotNull(message = "Status nao pode ser vazio")
        Status status


) {
}
