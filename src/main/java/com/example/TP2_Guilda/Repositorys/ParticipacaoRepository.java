package com.example.TP2_Guilda.Repositorys;

import com.example.TP2_Guilda.model.aventura.Missao;
import com.example.TP2_Guilda.model.aventura.Participacao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Long> {

    @EntityGraph(attributePaths = {"missao"})
    Optional<Participacao> findTop1ByAventureiroIdOrderByCriadoEmDesc(Long id);

    Integer countByAventureiroId(Long id);
}
