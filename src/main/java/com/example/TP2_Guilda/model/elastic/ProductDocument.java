package com.example.TP2_Guilda.model.elastic;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter@Setter@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "guilda_loja")
public class ProductDocument {

    @Id
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private String raridade;
    private Double preco;
}
