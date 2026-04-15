package com.example.TP2_Guilda.repositorys;

import com.example.TP2_Guilda.model.aventura.Participacao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Long> {

    @EntityGraph(attributePaths = {"missao"})
    Optional<Participacao> findFirstByAventureiroIdOrderByCriadoEmDesc(Long id);

    Integer countByAventureiroId(Long id);
}
