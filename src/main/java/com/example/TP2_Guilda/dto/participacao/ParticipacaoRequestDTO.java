package com.example.TP2_Guilda.dto.participacao;

import com.example.TP2_Guilda.Enum.FuncaoMissao;
import com.example.TP2_Guilda.model.aventura.Participacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ParticipacaoRequestDTO(
        @PositiveOrZero(message = "Recomenpsa ouro deve ser maior ou igual a zero")
        Long recompensaOuro,

        @NotNull(message = "Funcao nao pode ser nula")
        FuncaoMissao funcao,

        @NotNull(message = "MVP nao pode ser nulo")
        Boolean mvp
) {
    public void atualizarEntidade(Participacao byId) {
        if (this.recompensaOuro != null) {
            byId.setRecompensaOuro(this.recompensaOuro);
        }
        if (this.funcao != null) {
            byId.setFuncaoMissao(this.funcao);
        }
        if (this.mvp != null) {
            byId.setMvp(this.mvp);
        }
    }
}
