package com.example.TP2_Guilda.DTO;

import com.example.TP2_Guilda.Enum.Especie;
import jakarta.validation.constraints.*;

public record CompanheiroCreateDTO(
   @NotBlank
        String nome,

   @NotNull
    Especie especie,

    @PositiveOrZero @Max(value = 100)
    Integer lealdade
    )
{}
