package com.joao.empresa.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TecnicoRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve ser válido.")
        @Size(max = 150)
        String email,

        @NotBlank(message = "A especialidade é obrigatória.")
        @Size(max = 100)
        String especialidade

) {
}
