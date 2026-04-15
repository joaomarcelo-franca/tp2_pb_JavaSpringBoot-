package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.dto.aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.dto.aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.dto.missao.*;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoRequestDTO;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoResponse;
import com.example.TP2_Guilda.dto.participacao.ParticipacaoResponseComMissaoDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.mappers.AventureiroMapper;
import com.example.TP2_Guilda.mappers.MissaoMapper;
import com.example.TP2_Guilda.mappers.OrganizationMapper;
import com.example.TP2_Guilda.mappers.ParticipacaoMapper;
import com.example.TP2_Guilda.repositorys.AventureiroRespository;
import com.example.TP2_Guilda.repositorys.MissaoRepository;
import com.example.TP2_Guilda.repositorys.OrganizacaoRepository;
import com.example.TP2_Guilda.repositorys.ParticipacaoRepository;
import com.example.TP2_Guilda.exceptions.AventureiroInativoException;
import com.example.TP2_Guilda.exceptions.EntidadeNaoLocalizada;
import com.example.TP2_Guilda.exceptions.MissaoNaoPlanejadaException;
import com.example.TP2_Guilda.exceptions.OrganizacaoInvalida;
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
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MissaoService {
    private final MissaoRepository missaoRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final AventureiroRespository aventureiroRespository;
    private final ParticipacaoRepository participacaoRepository;

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

        return MissaoMapper.toMissaoResponseResumoDTO(missao);
    }

    //    Salvar Participacao
    public ParticipacaoResponseComMissaoDTO registrarParticipacao(Long missaoId, Long aventureiroId, ParticipacaoRequestDTO dto) {

        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Missao não encontrada"));

        Aventureiro aventureiro = aventureiroRespository.findById(aventureiroId)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não encontrado"));


        if (aventureiro.getAtivo() == false){
            throw new AventureiroInativoException("Um aventureiro inativo não pode ser associado a novas missões");
        }

        if (missao.getStatus() != Status.PLANEJADA){
            throw new MissaoNaoPlanejadaException("A missão deve estar em estado Planejada para aceitar participantes");
        }

        if (!Objects.equals(aventureiro.getOrganizacao().getId(), missao.getOrganizacao().getId())){
            throw new OrganizacaoInvalida("Apenas aventureiros da mesma organização podem participar");
        }

        Participacao participacao = new Participacao(
                aventureiro,
                missao,
                dto.recompensaOuro(),
                dto.funcao(),
                dto.mvp()
        );

        participacaoRepository.save(participacao);

//      FIXME  Resumir esse abaixo com metodo
        missao.getParticipacoes().add(participacao);
        aventureiro.getParticipacoes().add(participacao);


        return ParticipacaoMapper.participacaoResponseComMissaoDTO2(participacao, aventureiro, missao);
    }



//    TODO Listagem de Missões
    public Page<MissaoResponseResumoDTO> listarMissaoPagiado(MissaoRequestDTO filtro, Pageable pageable, LocalDateTime criadoEm) {
        return missaoRepository.buscarComFiltroPaginado(
                filtro.status(),
                filtro.perigo(),
                criadoEm,
                pageable
        );
    }

//    TODO Detalhamento de Missao
    public MissaoResponseDetalharDTO listarMissaoCompleta(Long id){

        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Missao não foi encontrado"));

        List<ParticipacaoResponse> participacoesDTO = missao.getParticipacoes()
                .stream()
                .map(participacao -> {
                    AventureiroResumoDTO aventureiroResumoDTO = AventureiroMapper.toAventureiroResumoDTO(participacao.getAventureiro());
                    return new ParticipacaoResponse(
                            participacao.getId(),
                            aventureiroResumoDTO,
                            participacao.getFuncaoMissao(),
                            participacao.getRecompensaOuro(),
                            participacao.isMvp()
                    );
                })
                .toList();


        OrganizacaoResumoDTO  organizacaoResumoDTO = OrganizationMapper.toOrganizacaoResumoDTO(missao.getOrganizacao(), missao.getCriandoEm());

        return MissaoMapper.toMissaoResponseDetalharDTO(missao, organizacaoResumoDTO, participacoesDTO);


    }

//   TODO Relatório de Missões com Métricas
    public List<MissaoResponseMetricasDTO> listarMissaoMetricas(LocalDateTime dataInicio){
        return missaoRepository.relatorioMetricaMissoes(dataInicio);
    }



}
