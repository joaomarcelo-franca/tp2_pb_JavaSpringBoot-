package com.example.TP2_Guilda.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.TP2_Guilda.dto.elastic.ProdutoResponseDTO;
import com.example.TP2_Guilda.model.elastic.ProductDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProdutoService {
    private final ElasticsearchClient elasticsearchClient;

// TODO Busca por nome do produto:
    public List<ProdutoResponseDTO> buscarPorTexto(String texto) throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("nome")
                                .query(texto)
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProdutoResponseDTO::toDTO)
                .toList();

    }

// TODO Busca por descrição do produto:
    public List<ProdutoResponseDTO> buscarPorDescricao(String descricao) throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("nome")
                                .query(descricao)
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProdutoResponseDTO::toDTO)
                .toList();

    }

// TODO Busca por frase exata:
public List<ProdutoResponseDTO> buscarPorFraseExata(String frase) throws IOException{
    SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
            .index("guilda_loja")
            .query(q -> q
                    .matchPhrase(m -> m
                            .field("descricao")
                            .query(frase)
                    )
            ), ProductDocument.class
    );

    return search.hits().hits()
            .stream()
            .map(ProdutoResponseDTO::toDTO)
            .toList();

}

// TODO Busca fuzzy:
public List<ProdutoResponseDTO> buscarPorNomeFuzzy(String nome) throws IOException{
    SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
            .index("guilda_loja")
            .query(q -> q
                    .fuzzy(f -> f
                            .field("nome")
                            .value(nome)
                            .fuzziness("AUTO")
                    )
            ), ProductDocument.class
    );

    return search.hits().hits()
            .stream()
            .map(ProdutoResponseDTO::toDTO)
            .toList();

}

//    TODO Busca em múltiplos campos:

    public List<ProdutoResponseDTO> buscarPorMultiCampos(String frase) throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .multiMatch(m -> m
                                .fields("descricao", "nome")
                                .query(frase)
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProdutoResponseDTO::toDTO)
                .toList();

    }

//    TODO Busca textual + filtro por categoria:
//    TODO ⚠️ MEXER NA IDENTACAO
    public List<ProdutoResponseDTO> buscarPorFiltro(String termo, String filtro) throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .query(q -> q.bool(b -> b
                        .must(m -> m
                                .match(t -> t
                                        .field("descricao")
                                        .query(termo)))
                        .filter(f -> f
                                .term(t -> t
                                        .field("categoria")
                                        .value(v -> v.stringValue(filtro))))
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProdutoResponseDTO::toDTO)
                .toList();

    }

//    TODO Busca por faixa de preço:

    public List<ProdutoResponseDTO> buscarPorFaixaDePreco(Double min, Double maximo) throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .range(r -> r
                                .number(n -> n
                                        .field("preco")
                                        .gte(min)
                                        .lte(maximo)
                                )
                        )
                ), ProductDocument.class
        );

        return search.hits().hits()
                .stream()
                .map(ProdutoResponseDTO::toDTO)
                .toList();

    }

    // TODO Busca combinada:
// ⚠️
    public List<ProdutoResponseDTO> buscaCombinada(String categoria, String raridade, Double min, Double max) throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .query(q -> q.bool(b -> {
                    b.filter(f -> f.term(t -> t.field("categoria").value(v -> v.stringValue(categoria))));
                    b.filter(f -> f.term(t -> t.field("raridade").value(v -> v.stringValue(raridade))));
                    b.filter(f -> f.range(r -> r.number(n -> {
                        var number = n.field("preco");

                        if (min != null) {
                            number = number.gte(min);
                        }

                        if (max != null) {
                            number = number.lte(max);
                        }

                        return number;
                    })
                    ));
                return b;
                })
        ), ProductDocument.class);

        return search.hits().hits()
                .stream()
                .map(ProdutoResponseDTO::toDTO)
                .toList();

    }

// TODO Parte C – Agregações

//    TODO Quantidade de produtos por categoria:

    public Map<String, Long> agregarProdutosPorCategoria() throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("by_category", a -> a
                        .terms(t -> t
                                .field("categoria")
                        )
                ), ProductDocument.class
        );

        Aggregate agg = search.aggregations().get("by_category");

        List<StringTermsBucket> buckets = agg.sterms().buckets().array();

        Map<String, Long> mapa = new HashMap<>();

        for (StringTermsBucket bucket : buckets) {
            mapa.put(bucket.key().stringValue(), bucket.docCount());
        }

        return mapa;
    }


// TODO Quantidade de produtos por raridade:

    public Map<String, Long> agregarProdutosPorRarida() throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("by_raridade", a -> a
                        .terms(t -> t
                                .field("categoria")
                        )
                ), ProductDocument.class
        );

        Aggregate agg = search.aggregations().get("by_raridade");

        List<StringTermsBucket> buckets = agg.sterms().buckets().array();

        Map<String, Long> mapa = new HashMap<>();

        for (StringTermsBucket bucket : buckets) {
            mapa.put(bucket.key().stringValue(), bucket.docCount());
        }

        return mapa;
    }

//  TODO Preço médio dos produtos:

    public Double retornarPrecoMedio() throws IOException{
        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("avg_preco", a -> a
                        .avg(avg -> avg
                                .field("preco")
                        )
                ), ProductDocument.class
        );

        Aggregate agg = search.aggregations().get("avg_preco");
        return agg.avg().value();


    }

// TODO Faixas de preço:


    public Map<String, Long> agruparPorFaixaDePreco() throws IOException {

        SearchResponse<ProductDocument> search = elasticsearchClient.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("faixas_preco", a -> a
                                .range(r -> r
                                        .field("preco")
                                        .ranges(
                                                AggregationRange.of(ar -> ar
                                                        .to(100.0)
                                                        .key("Abaixo de 100")
                                                ),
                                                AggregationRange.of(ar -> ar
                                                        .from(100.0)
                                                        .to(300.0)
                                                        .key("De 100 a 300")
                                                ),
                                                AggregationRange.of(ar -> ar
                                                        .from(300.0)
                                                        .to(700.0)
                                                        .key("De 300 a 700")
                                                ),
                                                AggregationRange.of(ar -> ar
                                                        .from(700.0)
                                                        .key("Acima de 700")
                                                )
                                        )
                                )
                        ),
                ProductDocument.class
        );

        Aggregate agg = search.aggregations().get("faixas_preco");

        Map<String, Long> resultado = new LinkedHashMap<>();

        agg.range().buckets().array().forEach(bucket -> {
            resultado.put(bucket.key(), bucket.docCount());
        });

        return resultado;
    }





}
