package com.joao.empresa.dto.manutencao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record FinalizarManutencaoRequest(

        @NotNull(message = "O custo é obrigatório.")
        @PositiveOrZero(message = "O custo não pode ser negativo.")
        BigDecimal custo

) {
}

// a data final não vem do cliente, vem do Service automaticamente.