package com.example.TP1_Guilda.service;

import com.example.TP1_Guilda.DTO.*;
import com.example.TP1_Guilda.Enum.Classe;
import com.example.TP1_Guilda.exceptions.EntityNotFoundException;
import com.example.TP1_Guilda.exceptions.HeadersInvalidosException;
import com.example.TP1_Guilda.model.Aventureiro;
import com.example.TP1_Guilda.model.Companheiro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GuildaService {

    private final List<Aventureiro> banco = new ArrayList<>();

    public GuildaService() {
        for (int i = 1; i <= 100; i++) {

            Aventureiro a = new Aventureiro(
                    (long) i,
                    "Aventureiro " + i,
                    Classe.values()[i % Classe.values().length],
                    (i % 50) + 1
            );

            a.setAtivo(i % 2 == 0); // alterna true/false

            banco.add(a);
        }
    }

    public List<AventureiroResponseDTO> listar() {
        List<AventureiroResponseDTO> bancoDTO = banco.stream()
                .map(this::toDTO)
                .toList();
        return List.copyOf(bancoDTO);
    }
    public PagedAventureiroResponse<AventureiroResumoDTO> listarPaginadoComFiltro(AventureiroFiltroRequestDTO aventureiroFiltroRequestDTO, int page, int size) {

        List<String> erros = new ArrayList<>();

        if (page < 0) {
            erros.add("page não pode ser negativo");
        }

        if (size < 1 || size > 50) {
            erros.add("size deve estar entre 1 e 50");
        }

        if (!erros.isEmpty()) {
            throw new HeadersInvalidosException(erros);
        }

        List<AventureiroResumoDTO> bancoFiltrado = banco.stream()
                .filter(aventureiro -> (aventureiroFiltroRequestDTO.classe() == null || aventureiro.getClasse().equals(aventureiroFiltroRequestDTO.classe())) &&
                        (aventureiroFiltroRequestDTO.ativo() == null || aventureiro.getAtivo().equals(aventureiroFiltroRequestDTO.ativo())) &&
                        (aventureiroFiltroRequestDTO.nivel() == null || aventureiro.getNivel() >= aventureiroFiltroRequestDTO.nivel())
                )
                .sorted(Comparator.comparing(Aventureiro::getId))
                .map(this::toResumoDTO)
                .toList();

        int total = bancoFiltrado.size();
        int inicio = page * size;
        if (inicio >= total) {
            return new PagedAventureiroResponse<>(List.of(), page, size, total);
        }

        int fim = Math.min(inicio + size,total);

        List<AventureiroResumoDTO> pagina = bancoFiltrado.subList(inicio, fim);

        return new PagedAventureiroResponse<>(pagina, page, size, total);
    }
    public AventureiroResponseDTO salvar(AventureiroCreateDTO aventureiroDTO) {
        Long novoId = getProximoID() + 1L;
        Aventureiro aventureiroNovo = new Aventureiro(novoId, aventureiroDTO.nome(), aventureiroDTO.classe(), aventureiroDTO.nivel());
        aventureiroNovo.setAtivo(true);
        banco.add(aventureiroNovo);
        return toDTO(aventureiroNovo);
    }
    public AventureiroResponseDTO procurarPorID(Long id) {

        Optional<AventureiroResponseDTO> first = banco.stream()
                .map(this::toDTO)
                .filter(a -> a.id().equals(id))
                .findFirst();
        return first.orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));
    }
    public AventureiroResponseDTO atualizarPorID(Long id, AventureiroCreateDTO aventureiroDTO) {

        Aventureiro aventureiroAtualizado = banco.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));

        aventureiroAtualizado.setNome(aventureiroDTO.nome());
        aventureiroAtualizado.setClasse(aventureiroDTO.classe());
        aventureiroAtualizado.setNivel(aventureiroDTO.nivel());


        return toDTO(aventureiroAtualizado);
    }
    public AventureiroResponseDTO sairDaGuilda(Long id) {

        Aventureiro aventureiro = banco.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));

        aventureiro.setAtivo(false);

        return toDTO(aventureiro);
    }
    public AventureiroResponseDTO entrarNaGuilda(Long id) {
        Aventureiro aventureiro = banco.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));

        aventureiro.setAtivo(true);

        return toDTO(aventureiro);
    }
    public AventureiroResponseDTO adicionarCompanheiro(Long id, Companheiro companheiro) {

        Aventureiro aventureiro = banco.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));

        aventureiro.setCompanheiro(companheiro);

        return toDTO(aventureiro);
    }
    public AventureiroResponseDTO removeCompanheiro(Long id) {
        Aventureiro aventureiro = banco.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro nao localizado"));

        aventureiro.setCompanheiro(null);

        return toDTO(aventureiro);
    }
    private Long getProximoID(){
        return banco.stream()
                .map(Aventureiro::getId)
                .max(Comparator.naturalOrder())
                .orElse(0L);

    }
    private AventureiroResponseDTO toDTO(Aventureiro a) {
        return new AventureiroResponseDTO(
                a.getId(),
                a.getNome(),
                a.getClasse(),
                a.getNivel(),
                a.getAtivo(),
                a.getCompanheiro()
        );
    }
    private AventureiroResumoDTO toResumoDTO(Aventureiro a) {
        return new AventureiroResumoDTO(
                a.getId(),
                a.getNome(),
                a.getClasse(),
                a.getNivel(),
                a.getAtivo()
        );
    }
    private boolean isNullOrBlank(String valor) {
        return valor == null || valor.isBlank();
    }



}
