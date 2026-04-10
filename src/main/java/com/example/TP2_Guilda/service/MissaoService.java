package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.DTO.Aventureiro.AventureiroResumoDTO;
import com.example.TP2_Guilda.DTO.Aventureiro.OrganizacaoResumoDTO;
import com.example.TP2_Guilda.DTO.Missao.*;
import com.example.TP2_Guilda.DTO.Participacao.ParticipacaoRequestDTO;
import com.example.TP2_Guilda.DTO.Participacao.ParticipacaoResponse;
import com.example.TP2_Guilda.DTO.Participacao.ParticipacaoResponseComMissaoDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.Repositorys.AventureiroRespository;
import com.example.TP2_Guilda.Repositorys.MissaoRepository;
import com.example.TP2_Guilda.Repositorys.OrganizacaoRepository;
import com.example.TP2_Guilda.Repositorys.ParticipacaoRepository;
import com.example.TP2_Guilda.exceptions.AventureiroInativoException;
import com.example.TP2_Guilda.exceptions.EntityNotFoundException;
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

import java.time.LocalDate;
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

        return new MissaoResponseResumoDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelDePerigo(),
                missao.getCriandoEm()
        );

    }

    //    Salvar Participacao
    public ParticipacaoResponseComMissaoDTO registrarParticipacao(Long missaoId, Long aventureiroId, ParticipacaoRequestDTO dto) {
        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException("Missao não encontrada"));

        Aventureiro aventureiro = aventureiroRespository.findById(aventureiroId)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));


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

        AventureiroResumoDTO aventureiroResumoDTO = new AventureiroResumoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );

        MissaoResponseResumoDTO missaoResumoDTO = new MissaoResponseResumoDTO(
                missao.getId(),
                missao.getTitulo(),
                missao.getStatus(),
                missao.getNivelDePerigo(),
                missao.getCriandoEm()
        );

        participacaoRepository.save(participacao);
        missao.getParticipacoes().add(participacao);
        aventureiro.getParticipacoes().add(participacao);

    return new ParticipacaoResponseComMissaoDTO(
            participacao.getId(),
            aventureiroResumoDTO,
            missaoResumoDTO,
            participacao.getRecompensaOuro(),
            participacao.getFuncaoMissao(),
            participacao.isMvp(),
            participacao.getCriadoEm()

    );
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

        // TODO VALIDAR
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missao não foi encontrado"));

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
