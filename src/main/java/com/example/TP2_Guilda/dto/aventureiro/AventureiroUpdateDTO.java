package com.example.TP2_Guilda.dto.aventureiro;

import com.example.TP2_Guilda.Enum.Classe;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import jakarta.validation.constraints.Positive;

public record AventureiroUpdateDTO(
        String nome,

        @Positive(message = "nivel tem que ser maior que ZERO")
        Integer nivel,
        Classe classe
) {
        public void atualizarEntidade(Aventureiro aventureiro) {
                if (this.nome != null) {
                        aventureiro.setNome(this.nome);
                }
                if (this.nivel != null) {
                        aventureiro.setNivel(this.nivel);
                }
                if (this.classe != null) {
                        aventureiro.setClasse(this.classe);
                }
        }
}
