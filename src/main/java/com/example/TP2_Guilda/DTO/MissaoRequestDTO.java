package com.example.TP2_Guilda.DTO;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;

import java.time.LocalDateTime;

public record MissaoRequestDTO (
        Status status,
        NivelDePerigo perigo,
        LocalDateTime criadoEm
){}
