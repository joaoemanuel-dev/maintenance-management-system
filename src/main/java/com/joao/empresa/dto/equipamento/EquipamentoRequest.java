package com.joao.empresa.dto.equipamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EquipamentoRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(
                max = 150,
                message = "O nome deve possuir no máximo 150 caracteres."
        )
        String nome,

        @NotBlank(message = "O código de patrimônio é obrigatório.")
        @Size(
                max = 100,
                message = "O código de patrimônio deve possuir no máximo 100 caracteres."
        )
        String codigoPatrimonio,

        @NotNull(message = "A data de aquisição é obrigatória.")
        LocalDate dataAquisicao,

        @NotNull(message = "O ID da empresa é obrigatório.")
        @Positive(message = "O ID da empresa deve ser positivo.")
        Integer empresaId

) {
}
