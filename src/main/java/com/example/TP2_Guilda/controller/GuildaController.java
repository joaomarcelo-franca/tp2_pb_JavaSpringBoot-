package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.DTO.*;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import com.example.TP2_Guilda.service.GuildaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/guilda")
@Validated
public class GuildaController {
    private final GuildaService service;

//    @GetMapping()
//    public ResponseEntity<List<AventureiroResponseDTO>> getAll(){
//        return ResponseEntity.ok(service.listar());
//    }
//
//    // 1️⃣ Registrar aventureiro
//    @PostMapping
//    public ResponseEntity<AventureiroResponseDTO> create(@RequestBody @Valid AventureiroCreateDTO aventureiroDTO){
//        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(aventureiroDTO));
//    }
//


//    // 2️⃣ Listar aventureiros (com filtros e paginação)
//    @GetMapping("/filtro/paginado")
//    public ResponseEntity<List<AventureiroResumoDTO>> getAllFiltradoPaginado(@ModelAttribute AventureiroFiltroRequestDTO aventureiroFiltroRequestDTO,
//                                                    @RequestHeader(value = "X-Page", required = false, defaultValue = "0") int page,
//                                                    @RequestHeader(value = "X-Size", required = false, defaultValue = "10") int size){
//        PagedAventureiroResponse<AventureiroResumoDTO> pagedAventureiroResponse = service.listarPaginadoComFiltro(aventureiroFiltroRequestDTO, page, size);
//        return ResponseEntity.ok()
//                .header("X-Total-Count", String.valueOf(pagedAventureiroResponse.getTotal()))
//                .header("X-Page", String.valueOf(pagedAventureiroResponse.getPage()))
//                .header("X-Size", String.valueOf(pagedAventureiroResponse.getSize()))
//                .header("X-Total-Pages", String.valueOf(pagedAventureiroResponse.getTotalPages()))
//                .body(pagedAventureiroResponse.getContent());
//    }
//
////    3️⃣ Consultar aventureiro por id
////    Feito com Exception no service
//    @GetMapping("/{id}")
//    public ResponseEntity<AventureiroResponseDTO> getByID(@PathVariable @Positive Long id){
//        AventureiroResponseDTO byID = service.procurarPorID(id);
//        return ResponseEntity.ok(byID);
//    }
//
////    4️⃣ Atualizar dados do aventureiro
//    @PutMapping("/{id}")
//    public ResponseEntity<AventureiroResponseDTO> updateByID(@PathVariable @Positive Long id, @RequestBody AventureiroCreateDTO aventureiroDTO){
//        return ResponseEntity.ok(service.atualizarPorID(id, aventureiroDTO));
//    }
//
//
////    5️⃣ Encerrar vínculo com a guilda
//    @PatchMapping("/{id}/sair")
//    public ResponseEntity<AventureiroResponseDTO> exitGuilda(@PathVariable @Positive Long id){
//        return ResponseEntity.ok(service.sairDaGuilda(id));
//    }
//
////    6️⃣ Recrutar novamente
//    @PatchMapping("/{id}/entrar")
//    public ResponseEntity<AventureiroResponseDTO> joinGuilda(@PathVariable @Positive Long id){
//        return ResponseEntity.ok(service.entrarNaGuilda(id));
//    }
//
//
////    COMPANHEIRO
////    7️⃣ Definir ou substituir companheiro
//
//    @PatchMapping("/{id}/companheiro/add")
//    public ResponseEntity<AventureiroResponseDTO> addCompanheiro(@PathVariable @Positive Long id, @RequestBody @Valid Companheiro companheiro){
//        return ResponseEntity.ok().body(service.adicionarCompanheiro(id, companheiro));
//    }
//
////    8️⃣ Remover companheiro
//    @PatchMapping("/{id}/companheiro/remove")
//    public ResponseEntity<AventureiroResponseDTO> removeCompanheiro(@PathVariable @Positive Long id){
//        return ResponseEntity.ok().body(service.removeCompanheiro(id));
//    }



}
