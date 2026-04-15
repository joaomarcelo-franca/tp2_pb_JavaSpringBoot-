package com.example.TP2_Guilda.controller;

import com.example.TP2_Guilda.dto.elastic.ProdutoResponseDTO;
import com.example.TP2_Guilda.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loja")
@AllArgsConstructor
public class ProdutoController {
    private ProdutoService produtoService;

    @GetMapping("/busca/nome")
    public List<ProdutoResponseDTO> listarProdutosPorNome(@RequestParam String termo) throws IOException {
        return produtoService.buscarPorTexto(termo);
    }

    @GetMapping("/busca/descricao")
    public List<ProdutoResponseDTO> listarProdutosPorDescricao(@RequestParam String termo) throws IOException {
        return produtoService.buscarPorDescricao(termo);
    }

    @GetMapping("/busca/frase")
    public List<ProdutoResponseDTO> listarProdutosPorFrase(@RequestParam String termo) throws IOException {
        return produtoService.buscarPorFraseExata(termo);
    }


    @GetMapping("/busca/fuzzy")
    public List<ProdutoResponseDTO> listarProdutosPorNomeFuzzy(@RequestParam String termo) throws IOException {
        return produtoService.buscarPorNomeFuzzy(termo);
    }

    @GetMapping("/busca/multicampos")
    public List<ProdutoResponseDTO> listarProdutosPorMultiCampos(@RequestParam String termo) throws IOException {
        return produtoService.buscarPorMultiCampos(termo);
    }

    @GetMapping("/busca/com-filtro")
    public List<ProdutoResponseDTO> listarProdutosPorTermoECategoria(@RequestParam String termo, @RequestParam String categoria) throws IOException {
        return produtoService.buscarPorFiltro(termo, categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoResponseDTO> listarProdutosPorTermoECategoria(@RequestParam Double min, @RequestParam Double max) throws IOException {
        return produtoService.buscarPorFaixaDePreco(min, max);
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoResponseDTO> listarProdutosAvancada(@RequestParam String categoria, @RequestParam String raridade, @RequestParam Double min, @RequestParam Double max) throws IOException {
        return produtoService.buscaCombinada(categoria, raridade, min, max);
    }

    @GetMapping("/agregacoes/por-categoria")
    public Map<String, Long> agregarProdutosPorCategoria() throws IOException {
        return produtoService.agregarProdutosPorCategoria();
    }

    @GetMapping("/agragacoes/por-raridade")
    public Map<String, Long> agregarProdutosPorRaridade() throws IOException {
        return produtoService.agregarProdutosPorRarida();
    }

    @GetMapping("/agragacoes/preco-medio")
    public Double agregarProdutosPorPrecoMedio() throws IOException {
        return produtoService.retornarPrecoMedio();
    }

    @GetMapping("/agragacoes/faixa-preco")
    public Map<String, Long> agregarProdutosPorFaixaPreco() throws IOException {
        return produtoService.agruparPorFaixaDePreco();
    }
}