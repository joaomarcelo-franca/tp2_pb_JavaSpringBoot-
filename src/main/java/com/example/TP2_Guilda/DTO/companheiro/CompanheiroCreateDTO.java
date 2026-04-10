package com.example.TP2_Guilda.DTO.companheiro;

import com.example.TP2_Guilda.Enum.Especie;
import jakarta.validation.constraints.*;

public record CompanheiroCreateDTO(
   @NotBlank(message = "nome é obrigatório")
        String nome,

   @NotNull(message = "Especie é obrigatorio")
    Especie especie,

    @PositiveOrZero(message = "A lealdade não pode ser negativa") @Max(value = 100, message = "A lealdade deve ser no máximo 100")
    Integer lealdade
    )
{}
