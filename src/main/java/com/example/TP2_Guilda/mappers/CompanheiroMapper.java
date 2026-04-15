package com.example.TP2_Guilda.mappers;

import com.example.TP2_Guilda.dto.companheiro.CompanheiroResponseDTO;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public  class CompanheiroMapper {

    public static CompanheiroResponseDTO toCompanheiroResponseDTO(Companheiro companheiro){
        return new CompanheiroResponseDTO(
                companheiro.getId(),
                companheiro.getNome(),
                companheiro.getEspecie(),
                companheiro.getLealdade()
        );
    }


}
