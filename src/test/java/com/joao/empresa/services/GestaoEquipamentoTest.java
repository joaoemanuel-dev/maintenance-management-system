package com.joao.empresa.services;

import com.joao.empresa.builders.EquipamentoBuilder;
import com.joao.empresa.exceptions.*;
import com.joao.empresa.model.Equipamento;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GestaoEquipamentoTest {

    private GestaoEquipamento gestaoEquipamento;
    private Equipamento equipamentoNovo;

    @BeforeEach
    public void antesDeCadaMetodoInstanciaOObjeto(){
        gestaoEquipamento = new GestaoEquipamento();
        equipamentoNovo = EquipamentoBuilder.builder().comId(1).comCodigoPatrimonio("12345678").build();
    }

    @Test
    public void quandoMetodoBuscarPorIdForChamadoEExistirEquipamentoComIdBuscadoDeveRetornarEsseEquipamento(){

        gestaoEquipamento.cadastrarEquipamento(equipamentoNovo);

        Equipamento equipamento = gestaoEquipamento.buscarPorId(1);

        assertEquals(1, equipamento.getId());
    }

    @Test
    public void quandoMetodoBuscarPorIdForChamadoENaoExistirEquipamentoComOIdBuscadoDeveLancarExcecao(){
        assertThrows(EquipamentoNaoEncontradoException.class, () -> gestaoEquipamento.buscarPorId(2));
    }

    @Test
    public void quandoOMetodoCadastrarEquipamentoForChamadoSeJaExistirUmEquipamentoComOMesmoIdDeveLancarExcecao(){

        Equipamento equipamentoNovo2 = EquipamentoBuilder.builder().
                comId(1).comNome("Rolamento da empilhadeira").
                comCodigoPatrimonio("7272721278").
                comDataAquisicao(LocalDate.of(2013, 05, 02)).
                build();

        gestaoEquipamento.cadastrarEquipamento(equipamentoNovo2);

        assertThrows(EquipamentoJaCadastradoException.class, () -> gestaoEquipamento.cadastrarEquipamento(equipamentoNovo2));
    }

    @Test
    public void quandochamarCadastrarEquipamentoSeJaExistirUmEquipamentoComOMesmoCodigoPatrimonioDeveLancarExcecao(){

        Equipamento equipamentoNovo2 = EquipamentoBuilder.builder().
                comId(2).comNome("Rolamento da empilhadeira").
                comCodigoPatrimonio("12345678").
                comDataAquisicao(LocalDate.of(2013, 05, 02)).
                build();

        gestaoEquipamento.cadastrarEquipamento(equipamentoNovo);
        gestaoEquipamento.cadastrarEquipamento(equipamentoNovo2);

        assertThrows(EquipamentoJaCadastradoException.class, () -> gestaoEquipamento.cadastrarEquipamento(equipamentoNovo2));
    }

    @Test
    public void quandoCadastrarEquipamentoForChamadoSemConflitosDeIdECodigoDeveAdicionarEquipamentoAoSistema(){

        gestaoEquipamento.cadastrarEquipamento(equipamentoNovo);

        assertTrue(gestaoEquipamento.listarEquipamentos().contains(equipamentoNovo));
    }

}
