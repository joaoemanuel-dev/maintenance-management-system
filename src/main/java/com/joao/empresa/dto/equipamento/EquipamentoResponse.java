package com.joao.empresa.dto.equipamento;

import com.joao.empresa.model.Equipamento;

import java.time.LocalDate;

// transforma a entidade equipamento em equipamentoResponse, sem expor toda a entidade
public record EquipamentoResponse(

        Integer id,
        String nome,
        String codigoPatrimonio,
        LocalDate dataAquisicao,
        Integer empresaId,
        String empresaNome

) {

    public static EquipamentoResponse from(
            Equipamento equipamento
    ) {

        return new EquipamentoResponse(
                equipamento.getId(),
                equipamento.getNome(),
                equipamento.getCodigoPatrimonio(),
                equipamento.getDataAquisicao(),
                equipamento.getEmpresa().getId(),
                equipamento.getEmpresa().getNome()
        );
    }
}
