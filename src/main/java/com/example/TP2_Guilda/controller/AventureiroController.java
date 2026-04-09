package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.DTO.Aventureiro.AventureiroCreateDTO;
import com.example.TP2_Guilda.DTO.Aventureiro.AventureiroResponseDTO;
import com.example.TP2_Guilda.DTO.Aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.DTO.CompanheiroCreateDTO;
import com.example.TP2_Guilda.service.GuildaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/aventureiros")
@Validated
public class AventureiroController {
    private final GuildaService guildaService;

    @PostMapping
    public ResponseEntity<AventureiroResumoDTO> registrarAventureiro(@RequestBody @Valid AventureiroCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(guildaService.registrar(dto));
    }

    @PostMapping("/{id}/companheiro")
    public ResponseEntity<Void> registrarCompanheiro(@PathVariable Long id, @RequestBody @Valid CompanheiroCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(guildaService.registrarCompanheiro(id, dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AventureiroResponseDTO> getAventureiro(@PathVariable Long id){
        return ResponseEntity.ok().body(guildaService.listarAventureiroCompletoPorId(id));
    }



}
