package com.example.TP2_Guilda.DTO;

import java.util.List;

public record ErrorResponseDTO
        (
                String mensagem,
                List<String> detalhes
        )
{
}
