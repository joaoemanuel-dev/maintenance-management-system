package com.joao.empresa.services;

import com.joao.empresa.exceptions.EmpresaJaCadastradaException;
import com.joao.empresa.exceptions.EmpresaNaoEncontradaException;
import com.joao.empresa.model.Empresa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(1, empresa.getId());
    }

    @Test
    public void quandoMetodoBuscarPorIdForChamadoENaoExistirEmpresaComOIdBuscadoDeveLancarExcecao(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        Assertions.assertThrows(EmpresaNaoEncontradaException.class, () -> gestaoEmpresa.buscarPorId(2));

    }

    @Test
    public void quandoOMetodoCadastrarEmpresaForChamadoSeJaExistirUmaEmpresaComOMesmoIdDeveLancarExcecao(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova1 = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        Empresa empresaNova2 = new Empresa(1, "Vale do Rio Doce", "98390955", "Congonhas", "Produtora de chapas");
        gestaoEmpresa.cadastrarEmpresa(empresaNova1);

        Assertions.assertThrows(EmpresaJaCadastradaException.class, () -> gestaoEmpresa.cadastrarEmpresa(empresaNova2));
    }

    @Test
    public void quandoOMetodoCadastrarEmpresaForChamadoSeJaExistirUmaEmpresaComOMesmoCnpjDeveLancarExcecao(){

        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        Empresa empresaNova1 = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        Empresa empresaNova2 = new Empresa(2, "Vale do Rio Doce", "2023018977", "Congonhas", "Produtora de chapas");
        gestaoEmpresa.cadastrarEmpresa(empresaNova1);

        Assertions.assertThrows(EmpresaJaCadastradaException.class, () -> gestaoEmpresa.cadastrarEmpresa(empresaNova2));
    }

}