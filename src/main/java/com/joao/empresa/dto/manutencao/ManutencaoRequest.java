package com.joao.empresa.dto.manutencao;

import com.joao.empresa.model.Manutencao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ManutencaoRequest(

        @NotNull(message = "O tipo da manutenção é obrigatório.")
        Manutencao.TipoManutencao tipoManutencao,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotNull(message = "A data de início é obrigatória.")
        LocalDate dataInicio,

        @NotNull(message = "O ID do equipamento é obrigatório.")
        @Positive(message = "O ID do equipamento deve ser positivo.")
        Integer equipamentoId,

        @NotNull(message = "O ID do técnico é obrigatório.")
        @Positive(message = "O ID do técnico deve ser positivo.")
        Integer tecnicoId

) {
}
