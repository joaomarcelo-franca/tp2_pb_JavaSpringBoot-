package com.example.TP2_Guilda.repositorys;

import com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.dto.aventureiro.RakingAventureiroDTO;
import com.example.TP2_Guilda.Enum.Classe;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AventureiroRespository extends JpaRepository<Aventureiro, Long> {

    @Query("""
        select new com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO(
            a.id, a.nome, a.classe, a.nivel, a.ativo
        )
        from Aventureiro a
        where (:ativo IS NULL OR a.ativo = :ativo)
              AND (:classe IS NULL OR a.classe = :classe)
              AND (:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)
""")
    Page<AventureiroResumoDTO> buscarComFiltroPaginado(
            @Param("ativo") boolean ativo,
            @Param("classe") Classe classe,
            @Param("nivelMinimo") Integer nivelMinimo,
            Pageable pageable
    );

    Page<AventureiroResumoDTO> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    List<Aventureiro> findAllByOrderByIdAsc();

    @EntityGraph(value = "Aventureiro.completo")
    Optional<Aventureiro> findById(Long id);



    @Query("""
        select new com.example.TP2_Guilda.dto.aventureiro.RakingAventureiroDTO(
            a.id, a.nome, COUNT(p.id), COALESCE(SUM(p.recompensaOuro), 0L), SUM(case when p.mvp = true then 1L else 0L end)
        )
        from Participacao p
        join p.aventureiro a
        join p.missao m
        where (:status is null or m.status = :status)
        and (cast(:dataInicio as date) is null or p.criadoEm <= :dataInicio)
        group by a.id, a.nome
        order by COALESCE(SUM(p.recompensaOuro), 0L) desc
""")
    List<RakingAventureiroDTO> rankingPorFiltro(
            @Param("dataInicio")LocalDateTime dataInicio,
            @Param("status")Status status);


    Pageable id(Long id);


}
