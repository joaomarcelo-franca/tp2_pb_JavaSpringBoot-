package com.example.TP2_Guilda.dto.elastic;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.TP2_Guilda.model.elastic.ProductDocument;

public record ProdutoResponseDTO(
        String id,
        String nome,
        String descricao,
        String categoria,
        String raridade,
        Double preco,
        Double score
) {
    public static ProdutoResponseDTO toDTO(Hit<ProductDocument> hit) {
        ProductDocument doc = hit.source();
        return new ProdutoResponseDTO(
                hit.id(),
                doc.getNome(),
                doc.getDescricao(),
                doc.getCategoria(),
                doc.getRaridade(),
                doc.getPreco(),
                hit.score()
        );
    }
}
