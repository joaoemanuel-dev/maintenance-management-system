package com.joao.empresa.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GestorRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve ser válido.")
        @Size(max = 150)
        String email,

        @NotBlank(message = "A área responsável é obrigatória.")
        @Size(max = 100)
        String areaResponsavel

) {
}
