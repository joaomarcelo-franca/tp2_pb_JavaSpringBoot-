package com.example.TP1_Guilda.model;

import com.example.TP1_Guilda.Enum.Especie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Companheiro {
    @NotNull@NotBlank
    private String nome;

    @NotNull
    private Especie especie;
//    Entre 0 e 100
    @Min(value = 0)@Max(value = 100)
    private Integer lealdade;
}
