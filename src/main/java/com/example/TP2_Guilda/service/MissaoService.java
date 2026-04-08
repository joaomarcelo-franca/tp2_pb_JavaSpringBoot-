package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.DTO.*;
import com.example.TP2_Guilda.Repositorys.MissaoRepository;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Missao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MissaoService {
    private final MissaoRepository missaoRepository;

//    TODO Listagem de Missões
    public Page<MissaoResponseResumoDTO> listarMissaoPagiado(MissaoRequestDTO filtro, Pageable pageable) {
        return missaoRepository.buscarComFiltroPaginado(
                filtro.status(),
                filtro.perigo(),
                filtro.criadoEm(),
                pageable
        );
    }




//    TODO Detalhamento de Missao
    public MissaoResponseDetalharDTO listarMissaoCompleta(Long id){

        // TODO VALIDAR
        Missao missao = missaoRepository.findById(id).orElse(null);

        List<ParticipacaoResponse> participacoesDTO = missao.getParticipacoes()
                .stream()
                .map(participacao -> {
                    AventureiroResumoDTO aventureiroResumoDTO = new AventureiroResumoDTO(
                            participacao.getAventureiro().getId(),
                            participacao.getAventureiro().getNome(),
                            participacao.getAventureiro().getClasse(),
                            participacao.getAventureiro().getNivel(),
                            participacao.getAventureiro().getAtivo()
                    );
                    return new ParticipacaoResponse(
                            participacao.getId(),
                            aventureiroResumoDTO,
                            participacao.getFuncaoMissao(),
                            participacao.getRecompensaOuro(),
                            participacao.isMvp()
                    );
                })
                .toList();

        return new MissaoResponseDetalharDTO(
                missao.getId(),
                missao.getOrganizacao(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getCriandoEm(),
                participacoesDTO
        );

    }




//   TODO Relatório de Missões com Métricas
    public List<MissaoResponseMetricasDTO> listarMissaoMetricas(LocalDateTime dataInicio){
        return missaoRepository.relatorioMetricaMissoes(dataInicio);
    }
}
