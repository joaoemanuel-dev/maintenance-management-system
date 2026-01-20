package com.joao.empresa.services;

import com.joao.empresa.exceptions.EmpresaJaCadastradaException;
import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.model.Empresa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestaoEmpresaTest {

    @Test
    public void quandoMetodoBuscarPorIdForChamadoEExistirEmpresaComIdBuscadoDeveRetornarEssaEmpresa(){
        // cenário
        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        //execucao
        Empresa empresa = gestaoEmpresa.buscarPorId(1); // basta testar com um para ver se o método funcionar "TESTE UNITÁRIO"

        //verificacao
        assertEquals(1, empresa.getId());
    }

    @Test
    public void quandoMetodoBuscarPorIdForChamadoENaoExistirEmpresaComOIdBuscadoDeveLancarExcecao(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        assertThrows(EmpresaNaoEncontradaException.class, () -> gestaoEmpresa.buscarPorId(2));
    }

    @Test
    public void quandoOMetodoCadastrarEmpresaForChamadoSeJaExistirUmaEmpresaComOMesmoIdDeveLancarExcecao(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova1 = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        Empresa empresaNova2 = new Empresa(1, "Vale do Rio Doce", "98390955", "Congonhas", "Produtora de chapas");
        gestaoEmpresa.cadastrarEmpresa(empresaNova1);

        assertThrows(EmpresaJaCadastradaException.class, () -> gestaoEmpresa.cadastrarEmpresa(empresaNova2));
    }

    @Test
    public void quandoOMetodoCadastrarEmpresaForChamadoSeJaExistirUmaEmpresaComOMesmoCnpjDeveLancarExcecao(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova1 = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        Empresa empresaNova2 = new Empresa(2, "Vale do Rio Doce", "2023018977", "Congonhas", "Produtora de chapas");
        gestaoEmpresa.cadastrarEmpresa(empresaNova1);

        assertThrows(EmpresaJaCadastradaException.class, () -> gestaoEmpresa.cadastrarEmpresa(empresaNova2));
    }

    @Test
    public void quandoCadastrarEmpresaForChamadoSemConflitosDeIdECnpjDeveAdicionarEmpresaAoSistema(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        assertTrue(gestaoEmpresa.listarEmpresas().contains(empresaNova)); // a empresa cadastrada está no sistema?
    }

    @Test
    public void quandoAtualizarEmpresaForChamadoDeveAtualizarOsDadosDaEmpresaCadastrada(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);
        Empresa empresaAlterada = new Empresa(1, "Vale do Rio Doce", "2023018977", "Congonhas", "Produtora de chapas");

        gestaoEmpresa.atualizarEmpresa(empresaAlterada);

        // não compara os dois objetos de uma vez pq é arriscado: depende de equals e se algum falhar não sabe qual
        assertAll( // aqui os erros aparecem tudo de uma vez (sem isso, no primeiro erro o teste parava)
                () -> assertEquals(empresaAlterada.getNome(), empresaNova.getNome()),
                () -> assertEquals(empresaAlterada.getCnpj(), empresaNova.getCnpj()),
                () -> assertEquals(empresaAlterada.getEndereco(), empresaNova.getEndereco()),
                () -> assertEquals(empresaAlterada.getSegmento(), empresaNova.getSegmento())
        );
    }

    @Test
    public void quandoOMetodoExcluirEmpresaForChamadoAEmpresaDeveSerExcluidaDoSistema(){
        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        gestaoEmpresa.excluirEmpresa(1);

        assertFalse(gestaoEmpresa.listarEmpresas().contains(empresaNova));
    }

}