package com.joao.empresa.builders;

import com.joao.empresa.model.Gestor;

public class GestorBuilder {

    public static Gestor umGestor() {
        return new Gestor(
                "Gestor Teste",
                "gestor@email.com",
                "Produção"
        );
    }
}
