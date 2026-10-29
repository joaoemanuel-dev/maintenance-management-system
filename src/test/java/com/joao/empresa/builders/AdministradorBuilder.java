package com.joao.empresa.builders;

import com.joao.empresa.model.Administrador;

public class AdministradorBuilder {

    public static Administrador umAdministrador() {
        return new Administrador(
                "Administrador Teste",
                "admin@email.com",
                "Tecnologia"
        );
    }
}
