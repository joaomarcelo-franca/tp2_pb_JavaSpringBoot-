package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.DTO.Aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.DTO.Aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.DTO.Missao.*;
import com.example.TP2_Guilda.DTO.Participacao.ParticipacaoResponse;
import com.example.TP2_Guilda.Repositorys.AventureiroRespository;
import com.example.TP2_Guilda.Repositorys.MissaoRepository;
import com.example.TP2_Guilda.Repositorys.OrganizacaoRepository;
import com.example.TP2_Guilda.model.audit.Organizacao;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Missao;
import com.example.TP2_Guilda.model.aventura.Participacao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MissaoService {
    private final MissaoRepository missaoRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final AventureiroRespository aventureiroRespository;

    //Colocar na controller
    //    Salvar
    public MissaoResponseResumoDTO registrarMissao(MissaoCreateDTO dto, Long organizacaoId) {
        Organizacao organizacao = organizacaoRepository.findById(organizacaoId)
                .orElseThrow(() -> new RuntimeException("organizacao nao encontrada"));

        Missao missao = new Missao(
                dto.titulo(),
                organizacao,
                dto.nivelDePerigo(),
                dto.status()
        );

        organizacao.getMissoes().add(missao);
        missaoRepository.save(missao);

        return new MissaoResponseResumoDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelDePerigo(),
                missao.getCriandoEm()
        );

    }

//      TODO TERMINAR DE FAZER

    //    Salvar Participacao
    public void registrarParticipacao(Long missaoId, Long aventureiroId) {
        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RuntimeException("missao nao encontrada"));

        Aventureiro aventureiro = aventureiroRespository.findById(aventureiroId)
                .orElseThrow(() -> new RuntimeException("aventureiro noa encontrado"));

//        Um aventureiro inativo não pode ser associado a novas missões. (SERVICE)

//          Apenas aventureiros da mesma organização podem participar. (SERVICE)

//        Participacao Missao (Estados: PLANEJADA)
//A missão deve estar em estado compatível para aceitar participantes (SERVICE)



    }



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
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missao nao foi encontrado"));

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

        OrganizacaoResumoDTO  organizacaoResumoDTO = new OrganizacaoResumoDTO(
                missao.getOrganizacao().getId(),
                missao.getOrganizacao().getNome(),
                missao.getOrganizacao().isAtivo(),
                missao.getOrganizacao().getCriadoEm()
        );

        return new MissaoResponseDetalharDTO(
                missao.getId(),
                organizacaoResumoDTO,
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
