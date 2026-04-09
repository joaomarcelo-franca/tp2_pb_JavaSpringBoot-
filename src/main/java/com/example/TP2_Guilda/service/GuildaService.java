package com.example.TP2_Guilda.service;

import com.example.TP2_Guilda.DTO.*;
import com.example.TP2_Guilda.DTO.Aventureiro.*;
import com.example.TP2_Guilda.DTO.Missao.MissaoResponseResumoDTO;
import com.example.TP2_Guilda.Enum.Status;
import com.example.TP2_Guilda.Repositorys.*;
import com.example.TP2_Guilda.model.audit.Organizacao;
import com.example.TP2_Guilda.model.audit.Usuario;
import com.example.TP2_Guilda.model.aventura.Aventureiro;
import com.example.TP2_Guilda.model.aventura.Companheiro;
import com.example.TP2_Guilda.model.aventura.Participacao;
import lombok.AllArgsConstructor;
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
    private final  UsuarioRepository usuarioRepository;
    private final CompanheiroRepository companheiroRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final AventureiroRespository aventureiroRespository;
    private final ParticipacaoRepository  participacaoRepository;


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
                .orElseThrow(() -> new RuntimeException("Aventureiro nao foi encontrado"));

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

        return new AventureiroResponseDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo(),
                organizacaoResumoDTO,
                aventureiro.getCompanheiro(),  // Fazer um DTO
                aventureiro.getCriadoEm(),
                aventureiro.getAtualizadoEm(),
                participacoes,
                ultimaMissao
        );

    }

    //    //        TODO Ranking de Participação
    public List<RakingAventureiroDTO> gerarRanking(LocalDateTime dataInicio, Status status){
        return aventureiroRespository.rankingPorFiltro(dataInicio , status);
    }


//    SALVAR
    public AventureiroResumoDTO registrar(AventureiroCreateDTO dto){
        Organizacao organizacao = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new RuntimeException("Organizacao nao encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

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
    public Void registrarCompanheiro(Long id, CompanheiroCreateDTO dto){
        Aventureiro aventureiro = aventureiroRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Companheiro nao encontrado"));

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























//    public List<AventureiroResponseDTO> listar() {
//        List<AventureiroResponseDTO> bancoDTO = banco.stream()
//                .map(this::toDTO)
//                .toList();
//        return List.copyOf(bancoDTO);
//    }
//    public PagedAventureiroResponse<AventureiroResumoDTO> listarPaginadoComFiltro(AventureiroFiltroRequestDTO aventureiroFiltroRequestDTO, int page, int size) {
//
//        List<String> erros = new ArrayList<>();
//
//        if (page < 0) {
//            erros.add("page não pode ser negativo");
//        }
//
//        if (size < 1 || size > 50) {
//            erros.add("size deve estar entre 1 e 50");
//        }
//
//        if (!erros.isEmpty()) {
//            throw new HeadersInvalidosException(erros);
//        }
//
//        List<AventureiroResumoDTO> bancoFiltrado = banco.stream()
//                .filter(aventureiro -> (aventureiroFiltroRequestDTO.classe() == null || aventureiro.getClasse().equals(aventureiroFiltroRequestDTO.classe())) &&
//                        (aventureiroFiltroRequestDTO.ativo() == null || aventureiro.getAtivo().equals(aventureiroFiltroRequestDTO.ativo())) &&
//                        (aventureiroFiltroRequestDTO.nivel() == null || aventureiro.getNivel() >= aventureiroFiltroRequestDTO.nivel())
//                )
//                .sorted(Comparator.comparing(Aventureiro::getId))
//                .map(this::toResumoDTO)
//                .toList();
//
//        int total = bancoFiltrado.size();
//        int inicio = page * size;
//        if (inicio >= total) {
//            return new PagedAventureiroResponse<>(List.of(), page, size, total);
//        }
//
//        int fim = Math.min(inicio + size,total);
//
//        List<AventureiroResumoDTO> pagina = bancoFiltrado.subList(inicio, fim);
//
//        return new PagedAventureiroResponse<>(pagina, page, size, total);
//    }
//    public AventureiroResponseDTO salvar(AventureiroCreateDTO aventureiroDTO) {
//        Long novoId = getProximoID() + 1L;
//        Aventureiro aventureiroNovo = new Aventureiro(novoId, aventureiroDTO.nome(), aventureiroDTO.classe(), aventureiroDTO.nivel());
//        aventureiroNovo.setAtivo(true);
//        banco.add(aventureiroNovo);
//        return toDTO(aventureiroNovo);
//    }
//    public AventureiroResponseDTO procurarPorID(Long id) {
//
//        Optional<AventureiroResponseDTO> first = banco.stream()
//                .map(this::toDTO)
//                .filter(a -> a.id().equals(id))
//                .findFirst();
//        return first.orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
//    }
//    public AventureiroResponseDTO atualizarPorID(Long id, AventureiroCreateDTO aventureiroDTO) {
//
//        Aventureiro aventureiroAtualizado = banco.stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
//
//        aventureiroAtualizado.setNome(aventureiroDTO.nome());
//        aventureiroAtualizado.setClasse(aventureiroDTO.classe());
//        aventureiroAtualizado.setNivel(aventureiroDTO.nivel());
//
//
//        return toDTO(aventureiroAtualizado);
//    }
//    public AventureiroResponseDTO sairDaGuilda(Long id) {
//
//        Aventureiro aventureiro = banco.stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
//
//        aventureiro.setAtivo(false);
//
//        return toDTO(aventureiro);
//    }
//    public AventureiroResponseDTO entrarNaGuilda(Long id) {
//        Aventureiro aventureiro = banco.stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
//
//        aventureiro.setAtivo(true);
//
//        return toDTO(aventureiro);
//    }
//    public AventureiroResponseDTO adicionarCompanheiro(Long id, Companheiro companheiro) {
//
//        Aventureiro aventureiro = banco.stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
//
//        aventureiro.setCompanheiro(companheiro);
//
//        return toDTO(aventureiro);
//    }
//    public AventureiroResponseDTO removeCompanheiro(Long id) {
//        Aventureiro aventureiro = banco.stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
//
//        aventureiro.setCompanheiro(null);
//
//        return toDTO(aventureiro);
//    }
//    private Long getProximoID(){
//        return banco.stream()
//                .map(Aventureiro::getId)
//                .max(Comparator.naturalOrder())
//                .orElse(0L);
//
//    }
//    private AventureiroResponseDTO toDTO(Aventureiro a) {
//        return new AventureiroResponseDTO(
//                a.getId(),
//                a.getNome(),
//                a.getClasse(),
//                a.getNivel(),
//                a.getAtivo(),
//                a.getCompanheiro()
//        );
//    }
//    private AventureiroResumoDTO toResumoDTO(Aventureiro a) {
//        return new AventureiroResumoDTO(
//                a.getId(),
//                a.getNome(),
//                a.getClasse(),
//                a.getNivel(),
//                a.getAtivo()
//        );
//    }
//    private boolean isNullOrBlank(String valor) {
//        return valor == null || valor.isBlank();
//    }



}
