package com.joao.empresa.services;

import com.joao.empresa.builders.EquipamentoBuilder;
import com.joao.empresa.builders.ManutencaoBuilder;
import com.joao.empresa.exceptions.EquipamentoJaCadastradoException;
import com.joao.empresa.exceptions.ManutencaoJaCadastradaException;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.model.Manutencao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void quandoBuscarFinalizadasPorIdForChamadoDeveRetornarEssaManutencao(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);

        Manutencao manutencao = gestaoManutencao.buscarFinalizadasPorId(1);

        assertEquals(1, manutencao.getId());
    }

    @Test
    public void quandoOMetodoCadastrarManutencaoForChamadoSeJaExistirManutencaoComOMesmoIdDeveLancarExcecao(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);
        Manutencao manutencaoNova2 = ManutencaoBuilder.builder().
                comId(1).comDescricao("Luz da injeção eletrônica acendendo").
                comDataInicio(LocalDate.of(2026, 01, 28)).
                build();

        assertThrows(ManutencaoJaCadastradaException.class, () -> gestaoManutencao.cadastrarManutencao(manutencaoNova2));
    }


}
