package com.joao.empresa.services;

import com.joao.empresa.builders.EquipamentoBuilder;
import com.joao.empresa.model.Equipamento;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestaoEquipamentoTest {

    private GestaoEquipamento gestaoEquipamento;
    private Equipamento equipamentoNovo;

    @BeforeEach
    public void antesDeCadaMetodoInstanciaOObjeto(){
        gestaoEquipamento = new GestaoEquipamento();
        equipamentoNovo = EquipamentoBuilder.builder().comId(1).build();
    }

    @Test
    public void quandoMetodoBuscarPorIdForChamadoEExistirEquipamentoComIdBuscadoDeveRetornarEsseEquipamento(){

        gestaoEquipamento.cadastrarEquipamento(equipamentoNovo);

        Equipamento equipamento = gestaoEquipamento.buscarPorId(1);

        assertEquals(1, equipamento.getId());
    }

}
