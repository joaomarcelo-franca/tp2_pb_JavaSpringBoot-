package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.DTO.Aventureiro.*;
import com.example.TP2_Guilda.DTO.companheiro.CompanheiroCreateDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.service.GuildaService;
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

    @GetMapping("/filtrado")
    public ResponseEntity<List<AventureiroResumoDTO>> listarAventureiroFiltro(
            AventureiroFiltroRequestDTO filtro,@PageableDefault(size = 10, sort = "id") Pageable pageable
    ){
        Page<AventureiroResumoDTO> page = guildaService.listarAventureiroComFiltro(filtro, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Page-Number", String.valueOf(page.getNumber()))
                .header("X-Page-Size", String.valueOf(page.getSize()))
                .header("X-Has-Next", String.valueOf(page.hasNext()))
                .body(page.getContent());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AventureiroResumoDTO>> listarAventureiroPorNome(
            @RequestParam String nome, @PageableDefault(size = 10, sort = "id") Pageable pageable
    ){
        Page<AventureiroResumoDTO> page = guildaService.listarAventureiroPorNome(nome, pageable);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .header("X-Total-Pages", String.valueOf(page.getTotalPages()))
                .header("X-Page-Number", String.valueOf(page.getNumber()))
                .header("X-Page-Size", String.valueOf(page.getSize()))
                .header("X-Has-Next", String.valueOf(page.hasNext()))
                .body(page.getContent());
    };

    @GetMapping("/completo/{id}")
    public ResponseEntity<AventureiroResponseDTO> buscarCompletoPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.ok().body(guildaService.listarAventureiroCompletoPorId(id));
    };

    @GetMapping("/ranking")
    public ResponseEntity<List<RakingAventureiroDTO>>  buscarRanking(
            @RequestParam(required = false)LocalDateTime data,
            @RequestParam(required = false) Status status
            ){
            return ResponseEntity.ok().body(guildaService.gerarRanking(data , status));
    };

    @PatchMapping("{id}/encerrar")
    public ResponseEntity<Void> encerrar(@PathVariable Long id){
        guildaService.encerrarVinculoComAGuilda(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/recrutar")
    public ResponseEntity<Void> recrutar(@PathVariable Long id){
        guildaService.recrutarNovamente(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/remover-companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id){
        guildaService.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            AventureiroUpdateDTO dto
    ) {
        guildaService.atualizarAventureiro(id, dto);
        return ResponseEntity.noContent().build();
    }
}
