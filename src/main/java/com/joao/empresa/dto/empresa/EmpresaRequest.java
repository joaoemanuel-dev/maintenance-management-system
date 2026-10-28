package com.joao.empresa.dto.empresa;

import com.joao.empresa.model.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Essa classe representa o JSON que entra na API
public record EmpresaRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 150, message = "O nome deve possuir no máximo 150 caracteres.")
        String nome,

        @NotBlank(message = "O CNPJ é obrigatório.")
        @Size(max = 18, message = "O CNPJ deve possuir no máximo 18 caracteres.")
        String cnpj,

        @Size(max = 255, message = "O endereço deve possuir no máximo 255 caracteres.")
        String endereco,

        @Size(max = 100, message = "O segmento deve possuir no máximo 100 caracteres.")
        String segmento,

        @NotNull(message = "O status é obrigatório.")
        Empresa.Status status

){

}
