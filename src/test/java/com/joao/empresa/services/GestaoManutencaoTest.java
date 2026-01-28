package com.joao.empresa.services;

import com.joao.empresa.builders.ManutencaoBuilder;
import com.joao.empresa.exceptions.ManutencaoJaCadastradaException;
import com.joao.empresa.model.Manutencao;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
        gestaoManutencao.finalizarManutencao(1); // só vai pras finalizadas depois que eu finalizo
        Manutencao manutencao = gestaoManutencao.buscarFinalizadasPorId(1);

        assertEquals(1, manutencao.getId());
    }

    @Test
    public void quandoOMetodoCadastrarManutencaoForChamadoSeJaExistirManutencaoAtivaComOMesmoIdDeveLancarExcecao(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);
        Manutencao manutencaoNova2 = ManutencaoBuilder.builder().
                comId(1).comDescricao("Luz da injeção eletrônica acendendo").
                comDataInicio(LocalDate.of(2026, 01, 28)).
                build();

        assertThrows(ManutencaoJaCadastradaException.class, () -> gestaoManutencao.cadastrarManutencao(manutencaoNova2));
    }

    @Test
    public void quandoOMetodoCadastrarManutencaoForChamadoSeJaExistirManutencaoFinalizadaComOMesmoIdDeveLancarExcecao(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);
        gestaoManutencao.finalizarManutencao(1);
        Manutencao manutencaoNova2 = ManutencaoBuilder.builder().
                comId(1).comDescricao("Luz da injeção eletrônica acendendo").
                comDataInicio(LocalDate.of(2026, 01, 28)).
                build();

        assertThrows(ManutencaoJaCadastradaException.class, () -> gestaoManutencao.cadastrarManutencao(manutencaoNova2));
    }

    @Test
    public void quandoCadastrarManutencaoForChamadoSemConflitosDeIdDeveAdicionarManutencaoAoSistema(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);

        assertTrue(gestaoManutencao.listarManutencoes().contains(manutencaoNova));
    }

    @Test
    public void quandoExisteManutencaoAtivaDoEquipamentoAssociadaRetornaTrue(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);

        assertTrue(gestaoManutencao.existeManutencaoDoEquipamento(1));
    }

    @Test
    public void quandoExisteManutencaoFinalizadaDoEquipamentoAssociadaRetornaTrue(){

        gestaoManutencao.cadastrarManutencao(manutencaoNova);
        gestaoManutencao.finalizarManutencao(1);

        assertTrue(gestaoManutencao.existeManutencaoDoEquipamento(1));
    }

    @Test
    public void quandoNaoExisteManutencaoDoEquipamentoAssociadaRetornaFalse(){

        assertFalse(gestaoManutencao.existeManutencaoDoEquipamento(2));
    }


}
