package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.DTO.Aventureiro.*;
import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.DTO.companheiro.CompanheiroCreateDTO;
import com.example.TP2_Guilda.DTO.companheiro.CompanheiroResponseDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.Repositorys.*;
import com.example.TP2_Guilda.exceptions.EntityNotFoundException;
import com.example.TP2_Guilda.model.audit.Organizacao;
import com.example.TP2_Guilda.model.audit.Usuario;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import com.example.TP2_Guilda.model.aventura.Participacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GuildaService {
    private final UsuarioRepository usuarioRepository;
    private final CompanheiroRepository companheiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final AventureiroRespository aventureiroRespository;
    private final ParticipacaoRepository participacaoRepository;


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
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não foi encontrado"));

        Integer participacoes = aventureiro.getParticipacoes().size();
//
        Participacao ultimaParticipacao = participacaoRepository.findFirstByAventureiroIdOrderByCriadoEmDesc(id)
                .orElse(null);

        MissaoResponseResumoDTO ultimaMissao = ultimaParticipacao != null ? new MissaoResponseResumoDTO(
                ultimaParticipacao.getMissao().getId(),
                ultimaParticipacao.getMissao().getTitulo(),
                ultimaParticipacao.getMissao().getStatus(),
                ultimaParticipacao.getMissao().getNivelDePerigo(),
                ultimaParticipacao.getMissao().getCriandoEm()
        ) : null;

        OrganizacaoResumoDTO organizacaoResumoDTO = new OrganizacaoResumoDTO(
                aventureiro.getOrganizacao().getId(),
                aventureiro.getOrganizacao().getNome(),
                aventureiro.getOrganizacao().isAtivo(),
                aventureiro.getCriadoEm()
        );

        CompanheiroResponseDTO companheiroResponseDTO = aventureiro.getCompanheiro() != null ? new CompanheiroResponseDTO(
                aventureiro.getCompanheiro().getId(),
                aventureiro.getCompanheiro().getNome(),
                aventureiro.getCompanheiro().getEspecie(),
                aventureiro.getCompanheiro().getLealdade()
        ) : null;

        return new AventureiroResponseDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo(),
                organizacaoResumoDTO,
                companheiroResponseDTO,
                aventureiro.getCriadoEm(),
                aventureiro.getAtualizadoEm(),
                participacoes,
                ultimaMissao
        );

    }

    //      TODO Ranking de Participação
    public List<RakingAventureiroDTO> gerarRanking(LocalDateTime dataInicio, Status status) {
        return aventureiroRespository.rankingPorFiltro(dataInicio, status);
    }

    //    SALVAR
    public AventureiroResumoDTO registrar(AventureiroCreateDTO dto) {
        Organizacao organizacao = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organizacao não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

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

        return new AventureiroResumoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }

    //  Registrar companheiro
    public Void registrarCompanheiro(Long id, CompanheiroCreateDTO dto) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        Companheiro companheiro = new Companheiro(
                aventureiro,
                dto.nome(),
                dto.especie(),
                dto.lealdade()
        );

        aventureiro.setCompanheiro(companheiro);
        companheiroRepository.save(companheiro);

        return null;
    }


    //    Encerrar Vinculo com a guilda 204
    @Transactional
    public void encerrarVinculoComAGuilda(Long id) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
        aventureiro.setAtivo(false);
    }

//    Recrutar novamente 204
    @Transactional
    public void recrutarNovamente(Long id) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
        aventureiro.setAtivo(true);

    }

//    Remover Companheiro 204
    @Transactional
    public void removerCompanheiro(Long id){
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
        aventureiro.setCompanheiro(null);
    }

//    Atualizar dados do aventureiro 204
    @Transactional
    public void atualizarAventureiro(Long id, AventureiroUpdateDTO dto) {
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        if (dto.nome() != null) {
            aventureiro.setNome(dto.nome());
        }

        if (dto.nivel() != null) {
            aventureiro.setNivel(dto.nivel());
        }

        if (dto.classe() != null) {
            aventureiro.setClasse(dto.classe());
        }


    }




}