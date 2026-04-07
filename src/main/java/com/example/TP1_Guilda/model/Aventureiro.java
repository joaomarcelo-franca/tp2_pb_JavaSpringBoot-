package com.example.TP1_Guilda.model;

import com.example.TP1_Guilda.Enum.Classe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter@Setter
public class Aventureiro {
//    Gerado pelo sistema
    private Long id;


    //    Obrigatorio e nao pode ser vazio
    private String nome;


    private Classe classe;

//    Maior ou Igual a 1
    private Integer nivel;

//    Recem criado = true;
    private Boolean ativo;

    @Valid
    private Companheiro companheiro;

    public Aventureiro(Long id, String nome, Classe classe, Integer nivel) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
    }
}
