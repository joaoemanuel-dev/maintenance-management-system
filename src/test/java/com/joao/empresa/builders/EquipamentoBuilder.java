package com.joao.empresa.builders;

import com.joao.empresa.model.Equipamento;

import java.time.LocalDate;

public class EquipamentoBuilder {

    public static Equipamento umEquipamento() {
        return new Equipamento(
                "Compressor",
                "PAT-001",
                LocalDate.of(2026, 1, 10),
                EmpresaBuilder.umaEmpresa()
        );
    }
}