package com.joao.empresa.builders;

import com.joao.empresa.model.Tecnico;

public class TecnicoBuilder {

    public static Tecnico umTecnico() {
        return new Tecnico(
                "Técnico Teste",
                "tecnico@email.com",
                "Elétrica"
        );
    }
}
