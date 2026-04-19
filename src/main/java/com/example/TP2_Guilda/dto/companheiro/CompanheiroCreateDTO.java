package com.example.TP2_Guilda.dto.companheiro;

import com.example.TP2_Guilda.Enum.Especie;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import jakarta.validation.constraints.*;

public record CompanheiroCreateDTO(
   @NotBlank(message = "nome é obrigatório")
        String nome,

   @NotNull(message = "Especie é obrigatorio")
    Especie especie,

    @PositiveOrZero(message = "A lealdade não pode ser negativa") @Max(value = 100, message = "A lealdade deve ser no máximo 100")
    Integer lealdade
    )
{
    public void atualizarCompanheiro(Companheiro byId) {
        if (this.nome != null) {
            byId.setNome(this.nome);
        }

        if (this.especie != null) {
            byId.setEspecie(this.especie);
        }

        if (this.lealdade != null) {
            byId.setLealdade(this.lealdade);
        }
    }
}
