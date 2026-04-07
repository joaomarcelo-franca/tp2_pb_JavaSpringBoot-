package com.example.TP1_Guilda.DTO;

import java.util.List;

public record ErrorResponseDTO
        (
                String mensagem,
                List<String> detalhes
        )
{
}
