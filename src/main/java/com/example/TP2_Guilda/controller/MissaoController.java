package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.DTO.Missao.MissaoCreateDTO;
import com.example.TP2_Guilda.DTO.Missao.MissaoRequestDTO;
import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.service.MissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/missoes")
@Validated
public class MissaoController {

    private final MissaoService missaoService;

//    Testar
    @PostMapping("/{id}/participacao")
    public ResponseEntity<MissaoResponseResumoDTO> registrarMissao(@PathVariable Long id, @Valid @RequestBody MissaoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(missaoService.registrarMissao(dto, id));
    }


}
