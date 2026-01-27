package com.joao.empresa.services;

import com.joao.empresa.builders.ManutencaoBuilder;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.model.Manutencao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestaoManutencaoTest {

    private GestaoManutencao gestaoManutencao;
    private Manutencao manutencaoNova;

    @BeforeEach
    public void antesDeCadaMetodoInstanciaOObjeto(){
        gestaoManutencao = new GestaoManutencao();
        manutencaoNova = ManutencaoBuilder.builder().comId(1).build();
    }

    @Test
    public void quandoBuscarAtivasPorIdForChamadoDeveRetornarEssaManutencao(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);

        Manutencao manutencao = gestaoManutencao.buscarAtivasPorId(1);

        assertEquals(1, manutencao.getId());
    }




}
