package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.dto.aventureiro.*;
import com.example.TP2_Guilda.dto.companheiro.CompanheiroCreateDTO;
import com.example.TP2_Guilda.dto.companheiro.CompanheiroResponseDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.repositorys.AventureiroRespository;
import com.example.TP2_Guilda.repositorys.CompanheiroRepository;
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

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/aventureiros")
@Validated
public class AventureiroController {
    private final GuildaService guildaService;

    // TODO Metodos GET

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> getAllAventureiros() {
        return ResponseEntity.ok().body(guildaService.getAll());
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

    @GetMapping("/companheiros/{id}")
    public ResponseEntity<CompanheiroResponseDTO> buscarCompanheiroPorId(@PathVariable Long id){
        return ResponseEntity.ok().body( guildaService.findById(id));
    }

//   TODO POST
    @PostMapping
    public ResponseEntity<AventureiroResumoDTO> registrarAventureiro(@RequestBody @Valid AventureiroCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(guildaService.registrar(dto));
    }


    @PostMapping("/{id}/companheiro")
    public ResponseEntity<CompanheiroResponseDTO> registrarCompanheiro(@PathVariable Long id, @RequestBody @Valid CompanheiroCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(guildaService.registrarCompanheiro(id, dto));
    }

//   TODO  PATCH

    @PatchMapping("{id}/encerrar")
    public ResponseEntity<Void> encerrar(@PathVariable Long id){
        guildaService.encerrarVinculoComAGuilda(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/recrutar")
    public ResponseEntity<Void> recrutar(@PathVariable Long id){
        guildaService.recrutarNovamente(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AventureiroUpdateDTO dto
    ) {
        guildaService.atualizarAventureiro(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/companheiro/{id}")
    public ResponseEntity<Void> atualizarCompanheiro(@PathVariable Long id, @Valid @RequestBody CompanheiroCreateDTO dto){
        guildaService.atualizarCompanheiro(id, dto);
        return ResponseEntity.ok().build();
    }

//  TODO  Delete
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerAventureiro(@PathVariable Long id){
       guildaService.removerAventureiro(id);
       return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/remover-companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id){
        guildaService.removerCompanheiro(id);
        return ResponseEntity.ok().build();
    }


}
