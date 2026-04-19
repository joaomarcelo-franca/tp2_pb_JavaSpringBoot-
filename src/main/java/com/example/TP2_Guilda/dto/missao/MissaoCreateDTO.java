package com.example.TP2_Guilda.dto.missao;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.model.aventura.Missao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record MissaoCreateDTO(

        @NotBlank(message = "Titulo nao pode ser vazio")
        @Length(min = 1, max = 150, message = "Titulo pode ter no máximo 150 caracteres")
        String titulo,

        @NotNull(message = "nivelDePerigo nao pode ser vazio")
        NivelDePerigo nivelDePerigo,

        @NotNull(message = "Status nao pode ser vazio")
        Status status


) {
   public void atualizarEntidade(Missao missao) {
           if (this.titulo != null) {
                   missao.setTitulo(this.titulo);
           }
           if (this.nivelDePerigo != null) {
                   missao.setNivelDePerigo(this.nivelDePerigo);
           }
           if (this.status != null) {
                   missao.setStatus(this.status);
           }
   }
}
