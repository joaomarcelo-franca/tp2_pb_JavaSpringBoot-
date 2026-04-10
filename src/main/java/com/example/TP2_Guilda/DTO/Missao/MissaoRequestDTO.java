package com.example.TP2_Guilda.DTO.Missao;

import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record MissaoRequestDTO (
        Status status,
        NivelDePerigo perigo
){}
