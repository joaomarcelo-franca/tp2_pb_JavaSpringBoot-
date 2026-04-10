package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.model.operacoes.PainelTaticoMissao;
import com.example.TP2_Guilda.service.PainelTaticoMissaoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/painel")
@AllArgsConstructor
public class PainelTaticoMissaoController {
    private PainelTaticoMissaoService painelTaticoMissaoService;

    @GetMapping
    public List<PainelTaticoMissao> listarTop10Missoes15Dias(){
        return painelTaticoMissaoService.listarTop10Missoes15Dias();
    }
}
