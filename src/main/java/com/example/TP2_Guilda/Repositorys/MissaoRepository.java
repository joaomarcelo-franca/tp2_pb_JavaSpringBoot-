package com.example.TP2_Guilda.Repositorys;

import com.example.TP2_Guilda.DTO.Missao.MissaoResponseMetricasDTO;
import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.Enum.NivelDePerigo;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.model.aventura.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @Query("""
    select new com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO(
        m.id, m.titulo, m.status, m.nivelDePerigo, m.criandoEm
        )
    from Missao m
        where (:status IS NULL or m.status = :status)
            and (:nivelDePerigo is null or m.nivelDePerigo = :nivelDePerigo)
            and (:data is null or m.criandoEm <= :data)
                order by m.id desc
    """)
    Page<MissaoResponseResumoDTO> buscarComFiltroPaginado(
            @Param("status") Status status,
            @Param("nivelDePerigo")NivelDePerigo nivelDePerigo,
            @Param("data")LocalDateTime criadoEm,
            Pageable pageable
            );


    @EntityGraph(value = "Missao.completo")
    Optional<Missao> findById(Long id);





    @Query("""
        select new com.example.TP2_Guilda.DTO.Missao.MissaoResponseMetricasDTO(
            m.titulo, m.status, m.nivelDePerigo, COUNT(p.id), COALESCE(sum(p.recompensaOuro), 0)
            )
        from Missao m
        left join m.participacoes p
            where (:data is null or m.criandoEm <= :data)
            group by m.titulo, m.status, m.nivelDePerigo
    """)
    List<MissaoResponseMetricasDTO> relatorioMetricaMissoes(
            @Param("data") LocalDateTime dataInicio
    );
}
