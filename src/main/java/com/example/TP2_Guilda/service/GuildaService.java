package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.dto.aventureiro.*;
import com.example.TP2_Guilda.dto.missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.dto.companheiro.CompanheiroCreateDTO;
import com.example.TP2_Guilda.dto.companheiro.CompanheiroResponseDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.exceptions.AventureiroJaPossuiCompanheiroException;
import com.example.TP2_Guilda.mappers.AventureiroMapper;
import com.example.TP2_Guilda.mappers.CompanheiroMapper;
import com.example.TP2_Guilda.mappers.MissaoMapper;
import com.example.TP2_Guilda.mappers.OrganizationMapper;
import com.example.TP2_Guilda.repositorys.*;
import com.example.TP2_Guilda.exceptions.EntidadeNaoLocalizada;
import com.example.TP2_Guilda.model.audit.Organizacao;
import com.example.TP2_Guilda.model.audit.Usuario;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import com.example.TP2_Guilda.model.aventura.Participacao;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GuildaService {
    private final UsuarioRepository usuarioRepository;
    private final CompanheiroRepository companheiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final AventureiroRespository aventureiroRespository;
    private final ParticipacaoRepository participacaoRepository;

    public List<AventureiroResumoDTO> getAll() {
        List<Aventureiro> all = aventureiroRespository.findAllByOrderByIdAsc();
        return all.stream().map(AventureiroMapper::toAventureiroResumoDTO).toList();

    }

    //    TODO Listagem de Aventureiros com Filtros
    public Page<AventureiroResumoDTO> listarAventureiroComFiltro(AventureiroFiltroRequestDTO filtro, Pageable pageable) {

        return aventureiroRespository.buscarComFiltroPaginado(
                filtro.ativo(),
                filtro.classe(),
                filtro.nivel(),
                pageable
        );
    }

    //    TODO Buscar por nome
    public Page<AventureiroResumoDTO> listarAventureiroPorNome(String nome, Pageable pageable) {
        return aventureiroRespository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    //    TODO Visualização Completa do Aventureiro
    public AventureiroResponseDTO listarAventureiroCompletoPorId(Long id) {


        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não foi encontrado"));

        Integer participacoes = aventureiro.getParticipacoes().size();

        Participacao ultimaParticipacao = participacaoRepository.findFirstByAventureiroIdOrderByCriadoEmDesc(id)
                .orElse(null);



        MissaoResponseResumoDTO ultimaMissao = ultimaParticipacao != null ? MissaoMapper.toMissaoResponseResumoDTO(ultimaParticipacao.getMissao()) : null;

        OrganizacaoResumoDTO organizacaoResumoDTO = OrganizationMapper.toOrganizacaoResumoDTO(aventureiro.getOrganizacao());

        CompanheiroResponseDTO companheiroResponseDTO = aventureiro.getCompanheiro() != null ? CompanheiroMapper.toCompanheiroResponseDTO(aventureiro.getCompanheiro()) : null;

        return AventureiroMapper.toAventureiroResponseDTO(aventureiro, organizacaoResumoDTO, companheiroResponseDTO, participacoes, ultimaMissao);

    }

    //      TODO Ranking de Participação
    public List<RakingAventureiroDTO> gerarRanking(LocalDateTime dataInicio, Status status) {
        return aventureiroRespository.rankingPorFiltro(dataInicio, status);
    }

    //    SALVAR
    public AventureiroResumoDTO registrar(AventureiroCreateDTO dto) {
        Organizacao organizacao = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new EntidadeNaoLocalizada("Organizacao não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.userId())
                .orElseThrow(() -> new EntidadeNaoLocalizada("Usuario não encontrado"));

        Aventureiro aventureiro = new Aventureiro(
                organizacao,
                usuario,
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );

        usuario.getAventureiros().add(aventureiro);
        organizacao.getAventureiros().add(aventureiro);
        aventureiroRespository.save(aventureiro);

        return AventureiroMapper.toAventureiroResumoDTO(aventureiro);

    }
//
    public void removerAventureiro(Long id){
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro nao encontrado"));

        aventureiroRespository.delete(aventureiro);
    }


    //  Registrar companheiro
    public CompanheiroResponseDTO registrarCompanheiro(Long id, CompanheiroCreateDTO dto) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não encontrado"));

        if (aventureiro.getCompanheiro() != null) {
            throw new AventureiroJaPossuiCompanheiroException("Aventureiro ja tem um companheiro assinado");
        }

        Companheiro companheiro = new Companheiro(
                aventureiro,
                dto.nome(),
                dto.especie(),
                dto.lealdade()
        );

        aventureiro.setCompanheiro(companheiro);
        companheiroRepository.save(companheiro);

        return CompanheiroMapper.toCompanheiroResponseDTO(companheiro);

    }

    @Transactional
    public void encerrarVinculoComAGuilda(Long id) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não encontrado"));
        aventureiro.setAtivo(false);
    }

    @Transactional
    public void recrutarNovamente(Long id) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não encontrado"));
        aventureiro.setAtivo(true);

    }

    @Transactional
    public void removerCompanheiro(Long id){
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não encontrado"));
        aventureiro.setCompanheiro(null);
    }

    @Transactional
    public void atualizarAventureiro(Long id, AventureiroUpdateDTO dto) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Aventureiro não encontrado"));

        dto.atualizarEntidade(aventureiro);
    }

    @Transactional
    public void atualizarCompanheiro(Long id, @Valid CompanheiroCreateDTO dto) {
        Companheiro byId = companheiroRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Companheiro nao localizado"));

        dto.atualizarCompanheiro(byId);
    }


    public CompanheiroResponseDTO findById(Long id) {
        Companheiro byId = companheiroRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoLocalizada("Companheiro nao localizado"));

        return CompanheiroMapper.toCompanheiroResponseDTO(byId);
    }
}