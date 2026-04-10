package com.example.TP2_Guilda.model.aventura;

import com.example.TP2_Guilda.Enum.Especie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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

    @JsonIgnore
    @OneToOne(mappedBy = "companheiro")
    private Aventureiro aventureiro;

    @Column(length = 120, nullable = false)
    private String nome;

    @Column(nullable = false)
    private Especie especie;

    @Column(nullable = false)
    private Integer lealdade;

    public Companheiro(Aventureiro aventureiro, String nome, Especie especie, Integer lealdade) {
        this.aventureiro = aventureiro;
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }

    protected Companheiro() {

    }
}
