package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.dto.missao.*;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoRequestDTO;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoResponseComMissaoDTO;
import com.example.TP2_Guilda.service.MissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/missoes")
@Validated
public class MissaoController {

    private final MissaoService missaoService;

    @PostMapping("/{id}/missao")
    public ResponseEntity<MissaoResponseResumoDTO> registrarMissao(@PathVariable Long id, @Valid @RequestBody MissaoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(missaoService.registrarMissao(dto, id));
    }

    @PostMapping("/{missaoId}/aventureiros/{aventureiroId}/participacoes")
    public ResponseEntity<ParticipacaoResponseComMissaoDTO> registrarParticipacao(@PathVariable Long missaoId, @PathVariable Long aventureiroId,@Valid @RequestBody  ParticipacaoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(missaoService.registrarParticipacao(missaoId, aventureiroId, dto));
    }

    @GetMapping
    public ResponseEntity<List<MissaoResponseResumoDTO>> listarMissoes(
            @RequestParam(required = false) LocalDateTime data,
            MissaoRequestDTO filtro,@PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        Page<MissaoResponseResumoDTO> page = missaoService.listarMissaoPagiado(filtro, pageable, data);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Page-Number", String.valueOf(page.getNumber()))
                .header("X-Page-Size", String.valueOf(page.getSize()))
                .header("X-Has-Next", String.valueOf(page.hasNext()))
                .body(page.getContent());
    };

    @GetMapping("/{id}")
    public ResponseEntity<MissaoResponseDetalharDTO> buscarMissaoPorIdCompleto(@PathVariable Long id){
        return ResponseEntity.ok().body(missaoService.listarMissaoCompleta(id));
    };

    @GetMapping("/metricas")
    public ResponseEntity<List<MissaoResponseMetricasDTO>> listarMissaoMetricas(
            @RequestParam(required = false) LocalDateTime date
    ){
        return ResponseEntity.ok().body(missaoService.listarMissaoMetricas(date));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizarMissaoPorId(@PathVariable Long id,@Valid @RequestBody MissaoCreateDTO dto){
        missaoService.atualizarMissao(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/participacao/{id}")
    public ResponseEntity<Void> atualizarParticipacaoPorId(@PathVariable Long id,@Valid @RequestBody ParticipacaoRequestDTO dto){
        missaoService.atualizarParticipacao(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerMissao(@PathVariable Long id){
        missaoService.removerMissao(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/participacao/{id}")
    public ResponseEntity<Void> removerParticipacaoPorId(@PathVariable Long id){
        missaoService.removerParticipacao(id);
        return ResponseEntity.noContent().build();
    }

    
}
