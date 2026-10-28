package com.joao.empresa.dto.manutencao;

import com.joao.empresa.model.Manutencao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ManutencaoResponse(

        Integer id,
        Manutencao.TipoManutencao tipoManutencao,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal custo,
        Manutencao.Status status,

        Integer equipamentoId,
        String equipamentoNome,

        Integer tecnicoId,
        String tecnicoNome

) {

    public static ManutencaoResponse from(
            Manutencao manutencao
    ) {

        return new ManutencaoResponse(
                manutencao.getId(),
                manutencao.getTipoManutencao(),
                manutencao.getDescricao(),
                manutencao.getDataInicio(),
                manutencao.getDataFim(),
                manutencao.getCusto(),
                manutencao.getStatus(),

                manutencao.getEquipamento().getId(),
                manutencao.getEquipamento().getNome(),

                manutencao.getTecnicoResponsavel().getId(),
                manutencao.getTecnicoResponsavel().getNome()
        );
    }
}
