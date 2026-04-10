package com.example.TP2_Guilda.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PainelTaticoMissaoServiceTest {

    @Autowired
    private PainelTaticoMissaoService service;

    @Test
    void testViewMaterialPainel(){
        assertEquals(service.listarTop10Missoes15Dias().size(), 10);
    }
}