package com.example.TP2_Guilda.dto.missao;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;

public record MissaoRequestDTO (
        Status status,
        NivelDePerigo perigo
){}
