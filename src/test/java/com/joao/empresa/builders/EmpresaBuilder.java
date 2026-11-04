package com.joao.empresa.builders;

import com.joao.empresa.model.Empresa;

public class EmpresaBuilder {

    public static Empresa umaEmpresa() {
        return new Empresa(
                "Empresa Teste",
                "12.345.678/0001-90",
                "Ouro Branco",
                "Industrial",
                Empresa.Status.ATIVADA
        );
    }
}
