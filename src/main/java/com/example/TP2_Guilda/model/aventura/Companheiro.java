package com.example.TP2_Guilda.model.aventura;

import com.example.TP2_Guilda.Enum.Especie;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Table(
        name = "companheiros",
        schema = "aventura"
)
@Entity
public class Companheiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "companheiro")
    private Aventureiro aventureiro;


    @NotNull@NotBlank
    @Column(length = 120, nullable = false)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private Especie especie;

    @Column(nullable = false)
    @Min(value = 0)@Max(value = 100)
    private Integer lealdade;
}
