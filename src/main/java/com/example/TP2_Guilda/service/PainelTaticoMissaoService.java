package com.example.TP2_Guilda.service;


import com.example.TP2_Guilda.Repositorys.PainelTaticoMissaoRepository;
import com.example.TP2_Guilda.model.operacoes.PainelTaticoMissao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PainelTaticoMissaoService {
    private final PainelTaticoMissaoRepository repository;

//  Habilito o Cache no metodo e passo um value para ela, pra depois identificalo para limpar
    @Cacheable("topMissoes15dias")
    public List<PainelTaticoMissao> listarTop10Missoes15Dias(){

        List<PainelTaticoMissao> all = repository.findAll();
        return all.stream()
                .filter(m -> m.getUltimaAtualizacao().isAfter(LocalDate.now().minusDays(15).atStartOfDay()))
                .sorted(Comparator.comparing(PainelTaticoMissao::getIndiceProntidao).reversed())
                .limit(10)
                .toList();
    };

//    Aqui habilito o Scheduled e definido para todos os dias 00:00
//    tambem e habilitado o CacheEvict para limpeza e e passado o valor da cache para identificar
    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "topMissoes15dias", allEntries = true)
    public void limparCacheAutomaticamente() {
        System.out.println("Cache limpo!");
    }
}
