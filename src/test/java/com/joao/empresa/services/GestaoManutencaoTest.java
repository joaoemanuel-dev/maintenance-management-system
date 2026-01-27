package com.joao.empresa.services;

import com.joao.empresa.builders.ManutencaoBuilder;
import com.joao.empresa.model.Manutencao;
import org.junit.jupiter.api.BeforeEach;

public class GestaoManutencaoTest {

    private GestaoManutencao gestaoManutencao;
    private Manutencao manutencaoNova;

    @BeforeEach
    public void antesDeCadaMetodoInstanciaOObjeto(){
        gestaoManutencao = new GestaoManutencao();
        manutencaoNova = ManutencaoBuilder.builder().comId(1).build();
    }




}
