package com.example.TP1_Guilda.DTO;

import com.example.TP1_Guilda.Enum.Classe;
import com.example.TP1_Guilda.model.Companheiro;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AventureiroResponseDTO(
         Long id,
         String nome,
         Classe classe,
         Integer nivel,
         Boolean ativo,
         Companheiro companheiro
) {
}
