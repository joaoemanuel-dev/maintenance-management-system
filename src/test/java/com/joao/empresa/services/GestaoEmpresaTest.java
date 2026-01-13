package com.joao.empresa.services;

import com.joao.empresa.model.Empresa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GestaoEmpresaTest {

    @Test
    public void quandoMetodoBuscarPorIdForChamadoDeveRetornarEmpresaComIdDoParametro(){
        // cenário
        Empresa empresaNova = new Empresa(1, "Gerdau Açominas", "2023018977", "Ouro Branco", "Produtora de aço");
        GestaoEmpresa gestaoEmpresa = new GestaoEmpresa();
        gestaoEmpresa.cadastrarEmpresa(empresaNova);

        //execucao
        Empresa empresa = gestaoEmpresa.buscarPorId(1); // basta testar com um para ver se o método funcionar "TESTE UNITÁRIO"

        //verificacao
        Assertions.assertEquals(1, empresa.getId());
    }

}